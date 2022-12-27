package com.example.data.api

internal interface NewsServerConfig {
    val baseUrl: String
}

internal object NewsServer {
    val prod = object : NewsServerConfig {
        override val baseUrl: String = PROD_HOST
    }
}

private const val PROD_HOST = "https://newsapi.org/"