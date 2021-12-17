package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wechatmoments.R
import com.google.accompanist.insets.statusBarsPadding

val MOMENTS_TOP_BAR_HEIGHT = 86.dp

@Composable
fun MomentsTopBar(alpha: Float, modifier: Modifier = Modifier) {
    val iconColor = if (alpha > 0.5f) Color.Black else Color.White
    val cameraIconId = if (alpha > 0.5f) R.drawable.ic_outline_camera else R.drawable.ic_baseline_camera

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.moments_top_bar_title),
                modifier = modifier
                    .fillMaxWidth()
                    .alpha(alpha),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .background(Color(0xFFEEEEEE).copy(alpha = alpha))
            .statusBarsPadding(),
        navigationIcon = {
            TopBarIcon(R.drawable.ic_baseline_arrow_back, "back", iconColor) {}
        },
        actions = {
            TopBarIcon(cameraIconId, "photos", iconColor) {}
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun TopBarIcon(
    iconId: Int,
    description: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = description,
            modifier = Modifier.size(24.dp),
            tint = iconColor
        )
    }
}