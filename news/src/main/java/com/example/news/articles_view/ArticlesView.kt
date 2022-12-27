package com.example.news.articles_view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.common.AppendErrorView
import com.example.core.ui.common.MyButton
import com.example.core.ui.theme.Dimens
import com.example.core.ui.theme.MyNewsAppTheme
import com.example.domain.model.Article
import com.example.core.ui.common.ComposablePagedGrid
import com.example.core.ui.common.RefreshErrorView
import kotlinx.coroutines.flow.emptyFlow
import com.example.core.R as CoreR

@Composable
internal fun ArticlesView(
    columns: Int,
    modifier: Modifier = Modifier,
    pagedArticles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit,
    onRefresh: () -> Unit,
    getErrorMessage: (Throwable) -> String,
) {
    Box(
        modifier = modifier
    ) {
        ArticlesGrid(
            columns = columns,
            modifier = Modifier.fillMaxSize(),
            pagedArticles = pagedArticles,
            onArticleClick = onArticleClick,
            onRefresh = onRefresh,
            getErrorMessage = getErrorMessage
        )
    }
}

@Composable
private fun ArticlesGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    pagedArticles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit = {},
    onRefresh: () -> Unit,
    getErrorMessage: (Throwable) -> String
) {
    ComposablePagedGrid(
        columns = columns,
        modifier = modifier,
        pagedItems = pagedArticles,
        onRefresh = onRefresh,
        itemContent = { article ->
            ArticleItemView(
                article = article,
                onArticleClick = onArticleClick
            )
        },
        refreshErrorContent = { modifier, throwable ->
            RefreshErrorView(
                modifier = modifier
                    .padding(Dimens.spacing12),
                message = getErrorMessage(throwable)
            )
        },
        appendErrorContent = { modifier, throwable, retry ->
            AppendErrorView(
                modifier = modifier
                    .padding(Dimens.spacing12),
                message = getErrorMessage(throwable),
                buttonsContent = {
                    MyButton(
                        title = stringResource(CoreR.string.Click_to_retry),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                        onClick = { retry() }
                    )
                }
            )
        }
    )
}

@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun PreviewArticlesView() {
    MyNewsAppTheme {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState
        ) { padding ->
            ArticlesView(
                columns = 2,
                modifier = Modifier.padding(padding),
                pagedArticles = emptyFlow<PagingData<Article>>().collectAsLazyPagingItems(),
                onArticleClick = {},
                onRefresh = {},
                getErrorMessage = { "" },
            )
        }
    }
}