package com.example.news.article_details_view

import app.cash.turbine.test
import com.example.domain.model.Article
import com.example.news.FakeDispatcherProvider
import com.example.news.NewsCache
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ArticleDetailsViewModelTest {

    private lateinit var viewModel: ArticleDetailsViewModel
    private val newsCache: NewsCache = mockk(relaxed = true)

    @Test
    fun `article from news cache updates ui state`() = runTest {
        val article = Article(
            title = "title"
        )
        every { newsCache.article } returns MutableStateFlow(article)
        viewModel = ArticleDetailsViewModel(
            newsCache = newsCache,
            dispatcherProvider = FakeDispatcherProvider(
                testDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
        )

        viewModel.uiState.test {
            assertEquals(ArticleDetailsState(article), awaitItem())
        }
    }
}