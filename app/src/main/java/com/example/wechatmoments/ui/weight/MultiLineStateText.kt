package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MultiLineStateText(tweetContent: String) {
    val (lineState, setLineState) = remember { mutableStateOf(LineState.DEFAULT) }
    val buttonText = if (lineState == LineState.CLIP) "Expand" else "Clip"

    Column {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = tweetContent,
            onTextLayout = { result ->
                if (result.didOverflowHeight) setLineState(LineState.CLIP)
            },
            maxLines = lineState.maxLines
        )

        if (lineState != LineState.DEFAULT) {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        when (lineState) {
                            LineState.CLIP -> setLineState(LineState.EXPAND)
                            LineState.EXPAND -> setLineState(LineState.CLIP)
                        }
                    },
                text = buttonText,
                color = Color(0xFF4c6e92)
            )
        }
    }
}

enum class LineState(val maxLines: Int) {
    DEFAULT(5),
    CLIP(5),
    EXPAND(Int.MAX_VALUE)
}