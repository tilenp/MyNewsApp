package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface GetArticlesUseCase {

    operator fun invoke(
        articleRequest: ArticleRequest,
    ): Flow<PagingData<Article>>
}