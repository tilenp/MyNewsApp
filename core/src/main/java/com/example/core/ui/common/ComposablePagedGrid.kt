package com.example.core.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.core.ui.theme.Dimens
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun <T : Any> ComposablePagedGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    pagedItems: LazyPagingItems<T>,
    onRefresh: () -> Unit,
    itemContent: @Composable (T) -> Unit,
    refreshErrorContent: @Composable (Modifier, Throwable) -> Unit,
    appendErrorContent: @Composable (Modifier, Throwable, () -> Unit) -> Unit
) {

    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        modifier = modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = onRefresh,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
            )
        }
    ) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
            contentPadding = PaddingValues(Dimens.spacing8)
        ) {
            items(pagedItems.itemCount) { index ->
                pagedItems[index]?.let { itemContent(it) }
            }
            pagedItems.apply {
                when {
                    loadState.refresh is LoadState.Error && itemCount > 0 -> {
                        val error = loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                            refreshErrorContent(Modifier.fillMaxWidth(), error.error)
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                            LoadingView(modifier = Modifier.fillMaxSize())
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = loadState.append as LoadState.Error
                        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                            appendErrorContent(Modifier.fillMaxWidth(), error.error) { retry() }
                        }
                    }
                }
            }
        }
        pagedItems.apply {
            when {
                loadState.refresh is LoadState.NotLoading -> {
                    swipeRefreshState.isRefreshing = false
                }
                loadState.refresh is LoadState.Loading -> {
                    swipeRefreshState.isRefreshing = true
                }
                loadState.refresh is LoadState.Error && itemCount == 0 -> {
                    swipeRefreshState.isRefreshing = false
                    val error = loadState.refresh as LoadState.Error
                    refreshErrorContent(Modifier.fillMaxSize(), error.error)
                }
            }
        }
    }
}