package com.example.news.article_details_view

import com.example.domain.model.Article
import javax.annotation.concurrent.Immutable

@Immutable
internal data class ArticleDetailsState(
    val article: Article = Article()
)