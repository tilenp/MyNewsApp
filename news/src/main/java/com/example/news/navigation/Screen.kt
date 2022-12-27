package com.example.news.navigation

internal sealed class Screen(val route: String){
    object ArticlesScreen: Screen("ArticlesScreen")
    object ArticleDetailsScreen: Screen("ArticleDetailsScreen")
}