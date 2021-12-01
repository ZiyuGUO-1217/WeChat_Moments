package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(modifier: Modifier) {
    Divider(
        color = Color.White,
        modifier = modifier
            .fillMaxHeight()
            .width(1.dp)
    )
}