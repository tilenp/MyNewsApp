package com.example.data.datasource.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.model.ArticleRequest
import com.example.data.api.NewsApi
import com.example.data.model.dto.ArticleDto
import retrofit2.HttpException
import java.io.IOException

internal class ArticlePagingSource(
    private val newsApi: NewsApi,
    private val articleRequest: ArticleRequest,
) : PagingSource<Int, ArticleDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        val page = params.key ?: INITIAL_PAGE
        return try {
            val response = newsApi.getArticles(
                query = articleRequest.query,
                pageSize = params.loadSize,
                page = page,
            )
            val articles = response.articles ?: emptyList()
            val nextKey = if (articles.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                page + (params.loadSize / articleRequest.pageSize)
            }
            LoadResult.Page(
                data = articles,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val INITIAL_PAGE = 1
    }
}