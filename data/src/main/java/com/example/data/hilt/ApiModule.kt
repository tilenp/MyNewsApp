package com.example.data.hilt

import com.example.data.api.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {

    @Singleton
    @Provides
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    fun provideNewsServerConfig(): NewsServerConfig {
        return NewsServer.prod
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory
            .create(gson)
    }

    @Singleton
    @Provides
    fun provideNewsApi(
        logger: HttpLoggingInterceptor,
        newsServerConfig: NewsServerConfig,
        authorizationInterceptor: AuthorizationInterceptor,
        converterFactory: GsonConverterFactory
    ): NewsApi {
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(authorizationInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(newsServerConfig.baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(NewsApi::class.java)
    }
}