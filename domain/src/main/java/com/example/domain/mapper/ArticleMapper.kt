package com.example.domain.mapper

import com.example.core.mapper.Mapper
import com.example.data.model.dto.ArticleDto
import com.example.domain.model.Article
import com.example.domain.model.PublishedAt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ArticleMapper @Inject constructor() : Mapper<ArticleDto, Article> {

    override fun map(objectToMap: ArticleDto): Article {
        return Article(
            author = objectToMap.author.orEmpty(),
            title = objectToMap.title.orEmpty(),
            description = objectToMap.description.orEmpty(),
            urlToImage = objectToMap.urlToImage.orEmpty(),
            publishedAt = PublishedAt(objectToMap.publishedAt)
        )
    }
}