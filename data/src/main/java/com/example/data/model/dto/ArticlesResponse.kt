package com.example.data.model.dto

internal data class ArticlesResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleDto>? = null,
)