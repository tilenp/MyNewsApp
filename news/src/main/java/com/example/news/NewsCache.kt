package com.example.news

import com.example.domain.model.Article
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NewsCache @Inject constructor() {

    private val _article = MutableStateFlow(Article())
    val article: StateFlow<Article> = _article

    fun setArticle(article: Article) {
        _article.value = article
    }
}