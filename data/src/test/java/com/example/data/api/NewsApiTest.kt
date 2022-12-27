package com.example.data.api

import com.example.data.FileReader
import com.example.data.model.dto.ArticlesResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class NewsApiTest {

    private lateinit var api: NewsApi
    private lateinit var webServer: MockWebServer

    private val gson = GsonBuilder().create()

    @BeforeEach
    fun setUp() {
        webServer = MockWebServer()
        webServer.start()
        val serverURL = webServer.url("/").toString()

        val client = OkHttpClient.Builder()
            .build()

        api = Retrofit.Builder()
            .baseUrl(serverURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NewsApi::class.java)
    }

    @AfterEach
    fun cleanUp() {
        webServer.shutdown()
    }

    @Nested
    inner class GetArticlesEndpoint {

        private val responseFileName = "response_get_articles_200.json"
        private val query = "tesla"
        private val pageSize = 10
        private val page = 1

        private fun <T> getResponseAsObject(fileName: String, clazz: Class<T>): T {
            val json = FileReader.readFile(fileName)
            val bodyType: Type = TypeToken.get(clazz).type
            return gson.fromJson(json, bodyType)
        }

        private suspend fun callGetArticles(): ArticlesResponse {
            val json = FileReader.readFile(fileName = responseFileName)
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(json)
            webServer.enqueue(mockResponse)
            return api.getArticles(
                query = query,
                pageSize = pageSize,
                page = page
            )
        }

        @Test
        fun `getArticles performs a GET request`() = runBlocking {
            callGetArticles()

            val request = webServer.takeRequest()
            assertEquals("GET", request.method)
        }

        @Test
        fun `getArticles has correct path`() = runBlocking {
            callGetArticles()

            val request = webServer.takeRequest()
            assertEquals("/v2/everything?q=$query&pageSize=$pageSize&page=$page", request.path)
        }

        @Test
        fun `getArticles maps response correctly`() = runBlocking {
            val result = callGetArticles()

            val response = getResponseAsObject(fileName = responseFileName, ArticlesResponse::class.java)
            assertEquals(response, result)
        }
    }
}