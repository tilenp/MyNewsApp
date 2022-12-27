package com.example.core.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.theme.Dimens
import com.example.core.ui.theme.MyNewsAppTheme
import com.example.core.ui.theme.Sizes
import com.example.core.R as CoreR

@Composable
fun RefreshErrorView(
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                modifier = Modifier.size(Sizes.mediumImage),
                painter = painterResource(id = CoreR.drawable.nothing_to_show),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(Dimens.spacing8),
                text = message,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(Dimens.spacing8),
                text = stringResource(CoreR.string.Swipe_to_retry),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewRefreshErrorView() {
    MyNewsAppTheme {
        Surface {
            RefreshErrorView(
                message = "message",
            )
        }
    }
}