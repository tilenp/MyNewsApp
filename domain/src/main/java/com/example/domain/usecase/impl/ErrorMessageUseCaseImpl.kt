package com.example.domain.usecase.impl

import android.content.Context
import com.example.domain.model.ArticleErrorBody
import com.example.domain.usecase.ErrorMessageUseCase
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import com.example.core.R as coreR

@Singleton
internal class ErrorMessageUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : ErrorMessageUseCase {

    override fun invoke(throwable: Throwable?): String {
        return when (throwable) {
            is IOException -> context.getString(coreR.string.Network_not_available)
            is HttpException -> deserializeErrorBody(throwable)
            else -> context.getString(coreR.string.Unknown_error)
        }
    }

    private fun deserializeErrorBody(httpException: HttpException): String {
        return httpException.response()?.errorBody()?.string()?.takeIf { it.isNotBlank() }?.let { json ->
                gson.fromJson(json, ArticleErrorBody::class.java).message
            } ?: context.getString(coreR.string.Unknown_error)
    }
}