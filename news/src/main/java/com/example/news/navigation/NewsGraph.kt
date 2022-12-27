package com.example.news.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.animation.enterHorizontalTransition
import com.example.core.ui.animation.exitHorizontalTransition
import com.example.core.ui.animation.popEnterHorizontalTransition
import com.example.core.ui.animation.popExitHorizontalTransition
import com.example.core.ui.common.SearchAppBar
import com.example.news.article_details_view.ArticleDetailsView
import com.example.news.article_details_view.ArticleDetailsViewModel
import com.example.news.articles_view.ArticlesView
import com.example.news.articles_view.ArticlesViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.core.R as coreR

@ExperimentalAnimationApi
@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@Composable
fun NewsGraph(
    widthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.ArticlesScreen.route,
        enterTransition = { enterHorizontalTransition() },
        exitTransition = { exitHorizontalTransition() },
        popEnterTransition = { popEnterHorizontalTransition() },
        popExitTransition = { popExitHorizontalTransition() }
    ) {
        composable(
            route = Screen.ArticlesScreen.route,
        ) {
            ConfigureArticlesScreen(
                widthSizeClass = widthSizeClass,
                navController = navController
            )
        }
        composable(
            route = Screen.ArticleDetailsScreen.route,
        ) {
            ConfigureArticleDetailsScreen(
                navController = navController
            )
        }
    }
}

@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@Composable
private fun ConfigureArticlesScreen(
    widthSizeClass: WindowWidthSizeClass,
    navController: NavHostController,
) {
    val columns = when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> 1
        else -> 2
    }

    val viewModel = hiltViewModel<ArticlesViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagedArticles = viewModel.articles.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SearchAppBar(
                hint = stringResource(id = coreR.string.Search_articles),
                onSearchClick = viewModel::onSearchClick,
                initialQuery = state.initialQuery,
            )
        }
    ) { padding ->
        ArticlesView(
            columns = columns,
            modifier = Modifier.padding(padding),
            pagedArticles = pagedArticles,
            onArticleClick = { article ->
                viewModel.onArticleClick(article)
                navController.navigate(Screen.ArticleDetailsScreen.route)
            },
            onRefresh = viewModel::onRefresh,
            getErrorMessage = viewModel::getErrorMessage,
        )
    }
}

@ExperimentalLifecycleComposeApi
@ExperimentalCoroutinesApi
@Composable
private fun ConfigureArticleDetailsScreen(
    navController: NavHostController,
) {
    val viewModel = hiltViewModel<ArticleDetailsViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold { padding ->
        ArticleDetailsView(
            modifier = Modifier.padding(padding),
            state = state,
            onBackClick = { navController.popBackStack() },
        )
    }
}