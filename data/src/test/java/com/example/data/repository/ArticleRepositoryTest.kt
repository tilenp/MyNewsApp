package com.example.data.repository

import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.datasource.PagedArticleDataSource
import com.example.data.model.dto.ArticleDto
import com.example.data.repository.impl.ArticleRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArticleRepositoryTest {

    private lateinit var repository: ArticleRepository

    private val pagedArticleDataSource: PagedArticleDataSource = mockk()
    private val articleRequest: ArticleRequest = mockk()
    private val articleDtosFlpw: Flow<PagingData<ArticleDto>> = mockk()

    @BeforeEach
    fun setUp() {
        every { pagedArticleDataSource.getArticles(any()) } returns articleDtosFlpw
        repository = ArticleRepositoryImpl(
            pagedArticleDataSource = pagedArticleDataSource
        )
    }

    @Test
    fun `repository calls paged article data source with correct parameters`() {
        repository.getArticles(articleRequest)

        verify(exactly = 1) { pagedArticleDataSource.getArticles(articleRequest) }

    }

    @Test
    fun `repository returns a flow from paged article data source`() {
        val result = repository.getArticles(articleRequest)

        assertEquals(articleDtosFlpw, result)

    }
}