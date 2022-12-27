package com.example.core.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.theme.Dimens
import com.example.core.R as coreR

@Composable
fun SearchAppBar(
    hint: String,
    initialQuery: String,
    onSearchClick: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf(initialQuery) }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.spacing56),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Placeholder(
                    hint = hint
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                LeadingIcon()
            },
            trailingIcon = if (text.isNotEmpty()) {
                {
                    TrailingIcon(
                        onClick = { text = "" }
                    )
                }
            } else null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text)
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
private fun Placeholder(
    hint: String,
) {
    Text(
        modifier = Modifier
            .alpha(ContentAlpha.medium),
        text = hint,
        color = Color.White
    )
}

@Composable
private fun LeadingIcon() {
    IconButton(
        modifier = Modifier
            .alpha(ContentAlpha.medium),
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.White
        )
    }
}

@Composable
private fun TrailingIcon(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Icon",
            tint = Color.White
        )
    }
}

@Preview(
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        hint = stringResource(id = coreR.string.Search_articles),
        initialQuery = "tesla",
        onSearchClick = {}
    )
}

@Preview(
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun SearchAppBarHintPreview() {
    SearchAppBar(
        hint = stringResource(id = coreR.string.Search_articles),
        initialQuery = "",
        onSearchClick = {}
    )
}