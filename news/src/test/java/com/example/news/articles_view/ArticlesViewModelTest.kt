package com.example.news.articles_view

import app.cash.turbine.test
import com.example.core.model.ArticleRequest
import com.example.domain.model.Article
import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.GetArticlesUseCase
import com.example.news.FakeDispatcherProvider
import com.example.news.NewsCache
import com.example.news.articles_view.ArticlesState.Companion.INITIAL_QUERY
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.LinkedList

@ExperimentalCoroutinesApi
class ArticlesViewModelTest {

    private lateinit var viewModel: ArticlesViewModel
    private val getArticlesUseCase: GetArticlesUseCase = mockk(relaxed = true)
    private val newsCache: NewsCache = mockk(relaxed = true)
    private val getErrorMessageUseCase: ErrorMessageUseCase = mockk(relaxed = true)

    @Test
    fun `initial ui state test`() = runTest {
        viewModel = ArticlesViewModel(
            getArticlesUseCase = getArticlesUseCase,
            newsCache = newsCache,
            getErrorMessageUseCase = getErrorMessageUseCase,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )

        viewModel.uiState.test {
            assertEquals(ArticlesState(), awaitItem())
        }
    }

    @Test
    fun `error message is returned using GetErrorMessageUseCase`() = runTest {
        val message = "message"
        val throwable = Throwable()
        every { getErrorMessageUseCase.invoke(any()) } returns message

        viewModel = ArticlesViewModel(
            getArticlesUseCase = getArticlesUseCase,
            newsCache = newsCache,
            getErrorMessageUseCase = getErrorMessageUseCase,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )
        val result = viewModel.getErrorMessage(throwable)

        verify(exactly = 1) { getErrorMessageUseCase.invoke(throwable) }
        assertEquals(message, result)
    }

    @Test
    fun `onSearchClick triggers a load with new request parameters`() = runTest {
        val query = "query"
        every { getArticlesUseCase.invoke(any()) } returns emptyFlow()
        viewModel = ArticlesViewModel(
            getArticlesUseCase = getArticlesUseCase,
            newsCache = newsCache,
            getErrorMessageUseCase = getErrorMessageUseCase,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )

        viewModel.articles.test {
            // initial query
            val requestList = LinkedList<ArticleRequest>()
            verify(exactly = 1) { getArticlesUseCase.invoke(capture(requestList)) }
            with(requestList.last) {
                assertEquals(INITIAL_QUERY, this.query)
                assertEquals(10, this.pageSize)
            }

            // on search click
            viewModel.onSearchClick(query)

            // new query
            verify(exactly = 2) { getArticlesUseCase.invoke(capture(requestList)) }
            with(requestList.last) {
                assertEquals(query, this.query)
                assertEquals(10, this.pageSize)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onArticleClick updates newsCache`() = runTest {
        val article = Article(
            title = "title"
        )
        viewModel = ArticlesViewModel(
            getArticlesUseCase = getArticlesUseCase,
            newsCache = newsCache,
            getErrorMessageUseCase = getErrorMessageUseCase,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )
        viewModel.onArticleClick(article)

        verify(exactly = 1) { newsCache.setArticle(article) }
    }

    @Test
    fun `onRefresh triggers a load with the same request parameters`() = runTest {
        every { getArticlesUseCase.invoke(any()) } returns emptyFlow()
        viewModel = ArticlesViewModel(
            getArticlesUseCase = getArticlesUseCase,
            newsCache = newsCache,
            getErrorMessageUseCase = getErrorMessageUseCase,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )

        viewModel.articles.test {
            // initial query
            val requestList = LinkedList<ArticleRequest>()
            verify(exactly = 1) { getArticlesUseCase.invoke(capture(requestList)) }
            with(requestList.last) {
                assertEquals(INITIAL_QUERY, this.query)
                assertEquals(10, this.pageSize)
            }

            // on search click
            viewModel.onRefresh()

            // new query
            verify(exactly = 2) { getArticlesUseCase.invoke(capture(requestList)) }
            with(requestList.last) {
                assertEquals(INITIAL_QUERY, this.query)
                assertEquals(10, this.pageSize)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}