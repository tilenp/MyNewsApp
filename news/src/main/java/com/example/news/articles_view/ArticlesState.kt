package com.example.news.articles_view

import javax.annotation.concurrent.Immutable

@Immutable
internal data class ArticlesState(
    val initialQuery: String = INITIAL_QUERY,
) {
    companion object {
        const val INITIAL_QUERY = "tesla"
    }
}