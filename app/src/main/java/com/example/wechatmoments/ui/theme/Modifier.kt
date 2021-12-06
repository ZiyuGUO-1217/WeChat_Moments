package com.example.wechatmoments.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
    this.clickable(
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
        indication = null
    )
}