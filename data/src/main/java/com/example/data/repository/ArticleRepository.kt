package com.example.data.repository

import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.model.dto.ArticleDto
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(
        articleRequest: ArticleRequest
    ): Flow<PagingData<ArticleDto>>
}