package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wechatmoments.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleImageCell(modifier: Modifier = Modifier, imageUrl: String) {
    val (cellHeight, setCellHeight) = remember { mutableStateOf(72.dp) }
    val (cellWidth, setCellWidth) = remember { mutableStateOf(72.dp) }

    val painter = rememberImagePainter(
        data = imageUrl,
        onExecute = { _, current ->
            val scale = if (current.size.height >= current.size.width) {
                current.size.height / 216
            } else {
                current.size.width / 216
            }
            val scaledHeight = current.size.height / scale
            val scaledWidth = current.size.width / scale
            setCellHeight(scaledHeight.dp)
            setCellWidth(scaledWidth.dp)
            true
        },
        builder = {
            placeholder(R.drawable.defaultprofileimage)
        }
    )

    Image(
        painter = painter,
        contentDescription = "image",
        modifier = modifier.size(width = cellWidth, height = cellHeight),
        contentScale = ContentScale.Crop
    )
}
