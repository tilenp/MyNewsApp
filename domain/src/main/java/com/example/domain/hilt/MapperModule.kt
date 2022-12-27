package com.example.domain.hilt

import com.example.core.mapper.Mapper
import com.example.data.model.dto.ArticleDto
import com.example.domain.mapper.ArticleMapper
import com.example.domain.model.Article
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class MapperModule {

    @Binds
    abstract fun bindArticleMapper(articleMapper: ArticleMapper): Mapper<ArticleDto, Article>
}

