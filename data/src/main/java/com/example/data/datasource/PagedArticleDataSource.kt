package com.example.data.datasource

import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.model.dto.ArticleDto
import kotlinx.coroutines.flow.Flow

internal interface PagedArticleDataSource {

    fun getArticles(
        articleRequest: ArticleRequest
    ): Flow<PagingData<ArticleDto>>
}