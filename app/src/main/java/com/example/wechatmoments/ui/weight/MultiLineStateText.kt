package com.example.wechatmoments.ui.weight

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wechatmoments.R
import com.example.wechatmoments.ui.theme.clickableWithoutRipple
import com.example.wechatmoments.ui.theme.tertiaryBlue

private const val DEFAULT_MAX_LINES = 8
private const val CLIPPED_MAX_LINES = 5

@Composable
fun MultiLineStateText(tweetContent: String) {
    val (lineState, setLineState) = remember { mutableStateOf(LineState.DEFAULT) }
    val buttonText = if (lineState == LineState.CLIP) {
        stringResource(R.string.moments_tweet_content_expand)
    } else {
        stringResource(R.string.moments_tweet_content_clip)
    }
    val onClick = {
        when (lineState) {
            LineState.CLIP -> setLineState(LineState.EXPAND)
            LineState.EXPAND -> setLineState(LineState.CLIP)
            else -> Log.d("lineState:", "$lineState")
        }
    }

    val shouldBeClipped = lineState != LineState.DEFAULT
    Column {
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .clickableWithoutRipple {
                    if (shouldBeClipped) onClick()
                }
                .animateContentSize(),
            text = tweetContent,
            onTextLayout = { result ->
                if (result.didOverflowHeight) setLineState(LineState.CLIP)
            },
            maxLines = lineState.maxLines
        )

        if (shouldBeClipped) {
            ClipOrExpandButton(buttonText, onClick)
        }
    }
}

@Composable
private fun ClipOrExpandButton(buttonText: String, onClick: () -> Any) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickableWithoutRipple { onClick.invoke() },
        text = buttonText,
        color = tertiaryBlue
    )
}

enum class LineState(val maxLines: Int) {
    DEFAULT(DEFAULT_MAX_LINES),
    CLIP(CLIPPED_MAX_LINES),
    EXPAND(Int.MAX_VALUE)
}