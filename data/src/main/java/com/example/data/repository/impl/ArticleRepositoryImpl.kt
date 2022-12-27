package com.example.data.repository.impl

import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.datasource.PagedArticleDataSource
import com.example.data.model.dto.ArticleDto
import com.example.data.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ArticleRepositoryImpl @Inject constructor(
    private val pagedArticleDataSource: PagedArticleDataSource,
) : ArticleRepository {

    override fun getArticles(
        articleRequest: ArticleRequest
    ): Flow<PagingData<ArticleDto>> {
        return pagedArticleDataSource.getArticles(
            articleRequest = articleRequest,
        )
    }
}