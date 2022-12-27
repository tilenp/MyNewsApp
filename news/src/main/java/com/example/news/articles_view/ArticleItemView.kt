package com.example.news.articles_view

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.ui.theme.Dimens
import com.example.core.ui.theme.MyNewsAppTheme
import com.example.core.ui.theme.Sizes
import com.example.domain.model.Article
import com.example.core.R as coreR

@Composable
internal fun ArticleItemView(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(Dimens.spacing8),
        elevation = Dimens.spacing4
    ) {
        Column(
            modifier = modifier.clickable { onArticleClick(article) },
        ) {
            ArticleImage(
                modifier = Modifier
                    .height(Sizes.smallImage)
                    .fillMaxWidth(),
                article = article
            )
            ArticleTitle(
                modifier = Modifier
                    .padding(Dimens.spacing8),
                title = article.title
            )
        }
    }
}

@Composable
private fun ArticleImage(
    modifier: Modifier,
    article: Article,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(article.urlToImage)
            .crossfade(durationMillis = 200)
            .build(),
        placeholder = painterResource(coreR.drawable.image_placeholder),
        error = painterResource(coreR.drawable.broken_image),
        fallback = painterResource(coreR.drawable.broken_image),
        contentDescription = "ArticleImage",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun ArticleTitle(
    modifier: Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.subtitle1,
        overflow = TextOverflow.Ellipsis
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
private fun PreviewPhotoItemView() {
    MyNewsAppTheme {
        Surface {
            ArticleItemView(
                modifier = Modifier.height(Sizes.smallImage),
                article = Article(
                    title = "Article Title"
                ),
                onArticleClick = {}
            )
        }
    }
}
