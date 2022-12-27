package com.example.news.articles_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.model.ArticleRequest
import com.example.core.utils.DispatcherProvider
import com.example.domain.model.Article
import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.GetArticlesUseCase
import com.example.news.NewsCache
import com.example.news.articles_view.ArticlesState.Companion.INITIAL_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val newsCache: NewsCache,
    private val getErrorMessageUseCase: ErrorMessageUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val requestEventDispatcher = MutableSharedFlow<RequestEvent>()
    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState: StateFlow<ArticlesState> = _uiState
    val articles = requestEventDispatcher
        .scan(initial = articleRequestBuilder) { requestBuilder, event -> event.map(requestBuilder) }
        .map { requestBuilder -> requestBuilder.build() }
        .flatMapLatest { request ->
            getArticlesUseCase.invoke(
                articleRequest = request,
            )
        }.cachedIn(viewModelScope.plus(dispatcherProvider.io))

    fun getErrorMessage(throwable: Throwable?): String {
        return getErrorMessageUseCase.invoke(throwable)
    }

    fun onSearchClick(query: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            requestEventDispatcher.emit(RequestEvent.Search(query = query))
        }
    }

    fun onArticleClick(article: Article) {
        newsCache.setArticle(article)
    }

    fun onRefresh() {
        viewModelScope.launch(dispatcherProvider.main) {
            requestEventDispatcher.emit(RequestEvent.Refresh)
        }
    }

    private sealed interface RequestEvent {
        fun map(requestBuilder: ArticleRequest.Builder): ArticleRequest.Builder

        data class Search(val query: String) : RequestEvent {
            override fun map(requestBuilder: ArticleRequest.Builder): ArticleRequest.Builder {
                return requestBuilder.query(query)
            }
        }

        object Refresh : RequestEvent {
            override fun map(requestBuilder: ArticleRequest.Builder): ArticleRequest.Builder {
                return requestBuilder
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private val articleRequestBuilder = ArticleRequest.Builder(INITIAL_QUERY)
            .pageSize(PAGE_SIZE)
    }
}