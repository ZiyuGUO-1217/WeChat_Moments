package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wechatmoments.R

@Composable
fun MomentsTopBar(alpha: Float) {
    val iconColor = if (alpha > 0.5f) Color.Black else Color.White
    val cameraIconId = if (alpha > 0.5f) R.drawable.ic_outline_camera else R.drawable.ic_baseline_camera
    TopAppBar(
        title = {
            Text(
                text = "Moments",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            TopBarIcon(R.drawable.ic_baseline_arrow_back, "back", iconColor) {}
        },
        actions = {
            TopBarIcon(cameraIconId, "photos", iconColor) {}
        },
        backgroundColor = Color(0xFFEEEEEE).copy(alpha = alpha),
        elevation = 4.dp.takeIf { alpha == 1f } ?: 0.dp
    )
}

@Composable
private fun TopBarIcon(
    iconId: Int,
    description: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = description,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        tint = iconColor
    )
}