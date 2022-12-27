package com.example.news.article_details_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.DispatcherProvider
import com.example.domain.model.Article
import com.example.news.NewsCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
internal class ArticleDetailsViewModel @Inject constructor(
    private val newsCache: NewsCache,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val uiState = newsCache.article
        .map { article -> StateEvent.ArticleSelected(article = article) }
        .scan(initial = ArticleDetailsState()) { state, event -> event.map(state) }
        .stateIn(
            scope = viewModelScope.plus(dispatcherProvider.io),
            started = SharingStarted.WhileSubscribed(),
            initialValue = ArticleDetailsState()
        )

    private sealed interface StateEvent {
        fun map(state: ArticleDetailsState): ArticleDetailsState

        data class ArticleSelected(val article: Article) : StateEvent {
            override fun map(state: ArticleDetailsState): ArticleDetailsState {
                return state.copy(
                    article = article
                )
            }
        }
    }
}