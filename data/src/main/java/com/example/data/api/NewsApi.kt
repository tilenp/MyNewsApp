package com.example.data.api

import com.example.data.model.dto.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {

    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null,
    ): ArticlesResponse
}
