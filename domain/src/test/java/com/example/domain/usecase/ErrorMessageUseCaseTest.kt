package com.example.domain.usecase

import android.content.Context
import com.example.domain.model.ArticleErrorBody
import com.example.domain.usecase.impl.ErrorMessageUseCaseImpl
import com.google.gson.Gson
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import com.example.core.R as CoreR

class ErrorMessageUseCaseTest {

    private lateinit var useCase: ErrorMessageUseCase
    private val context: Context = mockk()
    private val gson: Gson = mockk()
    private val response: Response<*> = mockk()
    private val errorBody: ResponseBody = mockk()
    private val articleErrorBody: ArticleErrorBody = mockk()
    private val exception: HttpException = mockk()

    private val errorBodyString = "errorBodyString"
    private val ioExceptionMessage = "ioExceptionMessage"
    private val httpExceptionMessage = "httpExceptionMessage"
    private val throwableMessage = "throwableMessage"

    @BeforeEach
    fun setUp() {
        every { context.getString(CoreR.string.Network_not_available) } returns ioExceptionMessage
        every { context.getString(CoreR.string.Unknown_error) } returns throwableMessage
        every { gson.fromJson(any<String>(), ArticleErrorBody::class.java) } returns articleErrorBody
        every { exception.response() } returns response
        every { response.errorBody() } returns errorBody
        every { errorBody.string() } returns errorBodyString
        every { articleErrorBody.message } returns httpExceptionMessage
        useCase = ErrorMessageUseCaseImpl(
            context = context,
            gson = gson
        )
    }

    @Test
    fun `io exception test`() {
        val exception = IOException()
        val result = useCase.invoke(exception)

        verify(exactly = 1) { context.getString(CoreR.string.Network_not_available) }
        assertEquals(ioExceptionMessage, result)
    }

    @Test
    fun `http exception test`() {
        val result = useCase.invoke(exception)

        verify(exactly = 1) { gson.fromJson(errorBodyString, ArticleErrorBody::class.java) }
        assertEquals(httpExceptionMessage, result)
    }

    @Test
    fun `http exception without message test`() {
        val result = useCase.invoke(Throwable())

        verify { gson wasNot Called }
        assertEquals(throwableMessage, result)
    }

    @Test
    fun `throwable test`() {
        val result = useCase.invoke(Throwable())

        assertEquals(throwableMessage, result)
    }
}