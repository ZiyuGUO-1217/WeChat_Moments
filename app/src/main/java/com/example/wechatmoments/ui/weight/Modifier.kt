package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.RelocationRequester
import androidx.compose.ui.layout.relocationRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.delay

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
    this.clickable(
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
        indication = null
    )
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.bringIntoViewAfterImeAnimation() = composed {
    val imeInsets = LocalWindowInsets.current.ime
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val relocationRequester = remember { RelocationRequester() }

    if (imeInsets.isVisible && imeInsets.animationInProgress.not() && isFocused) {
        LaunchedEffect(Unit) {
            delay(100L)
            relocationRequester.bringIntoView()
        }
    }

    relocationRequester(relocationRequester)
}

@Composable
fun getStatusBarHeight(): Dp {
    val density = LocalDensity.current
    val resources = LocalContext.current.resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeight = resources.getDimension(resourceId)

    return (statusBarHeight / density.density).dp
}