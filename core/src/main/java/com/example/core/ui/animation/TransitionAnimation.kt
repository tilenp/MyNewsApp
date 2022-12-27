package com.example.core.ui.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun enterHorizontalTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(ANIMATION_DURATION),
        initialOffsetX = { fullWidth -> fullWidth }
    )
}

fun exitHorizontalTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(ANIMATION_DURATION),
        targetOffsetX = { fullWidth -> -fullWidth }
    )
}

fun popEnterHorizontalTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(ANIMATION_DURATION),
        initialOffsetX = { fullWidth -> -fullWidth }
    )
}

fun popExitHorizontalTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(ANIMATION_DURATION),
        targetOffsetX = { fullWidth -> fullWidth }
    )
}

private const val ANIMATION_DURATION = 400