package com.example.data.hilt

import com.example.data.datasource.PagedArticleDataSource
import com.example.data.datasource.impl.PagedArticleDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindPagedArticleDataSource(pagedArticleDataSourceImpl: PagedArticleDataSourceImpl): PagedArticleDataSource
}