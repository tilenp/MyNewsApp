package com.example.domain.usecase.impl

import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.mapper.Mapper
import com.example.core.model.ArticleRequest
import com.example.data.model.dto.ArticleDto
import com.example.data.repository.ArticleRepository
import com.example.domain.model.Article
import com.example.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetArticlesUseCaseImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val articleMapper: Mapper<ArticleDto, Article>
) : GetArticlesUseCase {

    override fun invoke(
        articleRequest: ArticleRequest
    ): Flow<PagingData<Article>> {
        return articleRepository.getArticles(
            articleRequest = articleRequest,
        ).map { pagingData ->
            pagingData.map { articleDto ->
                articleMapper.map(articleDto)
            }
        }
    }
}