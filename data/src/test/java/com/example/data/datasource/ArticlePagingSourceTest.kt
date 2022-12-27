package com.example.data.datasource

import androidx.paging.PagingSource
import com.example.core.model.ArticleRequest
import com.example.data.api.NewsApi
import com.example.data.datasource.impl.ArticlePagingSource
import com.example.data.model.dto.ArticleDto
import com.example.data.model.dto.ArticlesResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ArticlePagingSourceTest {

    private lateinit var pagingSource: ArticlePagingSource
    private val newsApi: NewsApi = mockk()
    private val articleRequest: ArticleRequest = mockk()

    private val articleDto: ArticleDto = mockk()
    private val articlesResponse: ArticlesResponse = mockk()
    private val articleDtos = listOf(articleDto)
    private val query = "query"
    private val loadSize = 10
    private val pageSize = 10

    @BeforeEach
    fun setUp() {
        every { articleRequest.query } returns query
        every { articleRequest.pageSize } returns pageSize
        coEvery { newsApi.getArticles(any(), any(), any()) } returns articlesResponse
        every { articlesResponse.articles } returns articleDtos
        pagingSource = ArticlePagingSource(
            newsApi = newsApi,
            articleRequest = articleRequest
        )
    }

    @Test
    fun `data source calls api with correct parameters`() = runTest {
        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = loadSize,
                placeholdersEnabled = false
            )
        )
        coVerify(exactly = 1) {
            newsApi.getArticles(
                query = query,
                pageSize = pageSize,
                page = any()
            )
        }
    }

    @Test
    fun `refresh test`() = runTest {
        val expected = PagingSource.LoadResult.Page(
            data = articleDtos,
            prevKey = null,
            nextKey = ArticlePagingSource.INITIAL_PAGE + 1
        )
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = loadSize,
                placeholdersEnabled = false
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `append test`() = runTest {
        val expected = PagingSource.LoadResult.Page(
            data = articleDtos,
            prevKey = 1,
            nextKey = 3
        )
        val actual = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = loadSize,
                placeholdersEnabled = false
            )
        )
        assertEquals(expected, actual)
    }
}