package com.example.domain.model

internal data class ArticleErrorBody(
    val status: String? = null,
    val code: String? = null,
    val message: String? = null,
)