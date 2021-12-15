package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
    this.clickable(
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
        indication = null
    )
}

@Composable
fun getStatusBarHeight(): Dp {
    val density = LocalDensity.current
    val resources = LocalContext.current.resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeight = resources.getDimension(resourceId)

    return (statusBarHeight / density.density).dp
}