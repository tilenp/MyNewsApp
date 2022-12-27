package com.example.news.article_details_view

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.ui.theme.Dimens
import com.example.core.ui.theme.MyNewsAppTheme
import com.example.core.ui.theme.Sizes
import com.example.domain.model.Article
import com.example.domain.model.PublishedAt
import com.example.news.R
import com.example.core.R as coreR

@Composable
internal fun ArticleDetailsView(
    modifier: Modifier = Modifier,
    state: ArticleDetailsState,
    onBackClick: () -> Unit,
) {
    val article = state.article
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimens.spacing16)
    ) {
        ArticleImage(
            modifier = Modifier
                .height(Sizes.mediumImage),
            url = article.urlToImage,
            onBackClick = onBackClick,
        )
        ArticleTitle(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.spacing16,
                    vertical = Dimens.spacing8
                ),
            title = article.title,
        )
        ArticleInfo(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.spacing16,
                    vertical = Dimens.spacing8
                ),
            author = article.author,
            publishedAt = article.publishedAt,
        )
        ArticleDescription(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.spacing16,
                    vertical = Dimens.spacing8
                ),
            description = article.description,
        )
    }
}

@Composable
private fun ArticleImage(
    modifier: Modifier = Modifier,
    url: String?,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(durationMillis = 200)
                .build(),
            placeholder = painterResource(coreR.drawable.image_placeholder),
            error = painterResource(coreR.drawable.broken_image),
            fallback = painterResource(coreR.drawable.broken_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(
                    start = Dimens.spacing8,
                    top = Dimens.spacing8
                ),
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(Sizes.mediumIcon)
                    .padding(Dimens.spacing12),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ArticleTitle(
    modifier: Modifier,
    title: String
) {
    if (title.isNotBlank()) {
        Text(
            modifier = modifier,
            text = title,
            style = MaterialTheme.typography.h6,
        )
    } else {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.title_not_available),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun ArticleInfo(
    modifier: Modifier,
    author: String,
    publishedAt: PublishedAt
) {
    Column(
        modifier = modifier
    ) {
        ArticleAuthor(
            modifier = Modifier.fillMaxWidth(),
            author = author,
        )
        ArticlePublishDate(
            modifier = Modifier.fillMaxWidth(),
            publishedAt = publishedAt,
        )
    }
}

@Composable
private fun ArticleAuthor(
    modifier: Modifier,
    author: String
) {
    if (author.isNotBlank()) {
        Text(
            modifier = modifier,
            text = String.format(stringResource(id = R.string.article_source_format), author),
            style = MaterialTheme.typography.caption,
        )
    } else {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.author_not_available),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun ArticlePublishDate(
    modifier: Modifier,
    publishedAt: PublishedAt
) {
    val publishDate = publishedAt.getFormattedTime(LocalContext.current)
    if (publishDate.isNotBlank()) {
        Text(
            modifier = modifier,
            text = publishDate,
            style = MaterialTheme.typography.caption,
        )
    } else {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.publish_date_not_available),
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
private fun ArticleDescription(
    modifier: Modifier,
    description: String
) {
    if (description.isNotBlank()) {
        Text(
            modifier = modifier,
            text = description,
            style = MaterialTheme.typography.body1,
        )
    } else {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.description_not_available),
            style = MaterialTheme.typography.caption,
        )
    }
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
private fun PreviewArticleDetailsView() {
    MyNewsAppTheme {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState
        ) { padding ->
            ArticleDetailsView(
                modifier = Modifier.padding(padding),
                state = getState(),
                onBackClick = {},
            )
        }
    }
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
private fun PreviewEmptyArticleDetailsView() {
    MyNewsAppTheme {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState
        ) { padding ->
            ArticleDetailsView(
                modifier = Modifier.padding(padding),
                state = ArticleDetailsState(),
                onBackClick = {},
            )
        }
    }
}

private fun getState(): ArticleDetailsState {
    return ArticleDetailsState(
        article = Article(
            author = "Paul R. La Monica",
            title = "Is the worst over for bitcoin and the rest of crypto?",
            description = "The meltdown of FTX has sent the price of bitcoin and...",
            urlToImage = "urlToImage",
            publishedAt = PublishedAt(
                publishedAt = "2022-11-25T15:36:44Z"
            )
        )
    )
}