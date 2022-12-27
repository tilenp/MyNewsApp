package com.example.domain.usecase


import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.model.dto.ArticleDto
import com.example.data.repository.ArticleRepository
import com.example.domain.mapper.ArticleMapper
import com.example.domain.usecase.impl.GetArticlesUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetArticlesUseCaseTest {

    private lateinit var useCase: GetArticlesUseCase

    private val articleRepository: ArticleRepository = mockk()
    private val articleMapper: ArticleMapper = mockk()

    private val articleRequest: ArticleRequest = mockk()
    private val articleDtoPagingData: PagingData<ArticleDto> = mockk(relaxed = true)
    private val articleDtoFlow = flowOf(articleDtoPagingData)

    @BeforeEach
    fun setUp() {
        every { articleRepository.getArticles(any()) } returns articleDtoFlow
        useCase = GetArticlesUseCaseImpl(
            articleRepository = articleRepository,
            articleMapper = articleMapper
        )
    }

    @Test
    fun `use case returns a flow from repository`() {
        useCase.invoke(articleRequest)

        verify(exactly = 1) { articleRepository.getArticles(articleRequest) }

    }
}