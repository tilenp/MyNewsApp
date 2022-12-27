package com.example.data.datasource.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.model.ArticleRequest
import com.example.data.api.NewsApi
import com.example.data.datasource.PagedArticleDataSource
import com.example.data.model.dto.ArticleDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PagedArticleDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi,
) : PagedArticleDataSource {

    override fun getArticles(
        articleRequest: ArticleRequest
    ): Flow<PagingData<ArticleDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = articleRequest.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArticlePagingSource(
                    newsApi = newsApi,
                    articleRequest = articleRequest,
                )
            }
        ).flow
    }
}