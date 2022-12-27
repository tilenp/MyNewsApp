package com.example.mynewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.example.core.ui.theme.MyNewsAppTheme
import com.example.news.navigation.NewsGraph
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalMaterial3WindowSizeClassApi
@ExperimentalCoroutinesApi
@ExperimentalLifecycleComposeApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNewsAppTheme {
                val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                NewsGraph(
                    widthSizeClass = widthSizeClass,
                    navController = rememberAnimatedNavController(),
                )
            }
        }
    }
}