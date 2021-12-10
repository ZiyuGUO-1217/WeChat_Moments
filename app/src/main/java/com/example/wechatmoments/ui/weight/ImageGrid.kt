package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.wechatmoments.R
import com.example.wechatmoments.model.Image
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.placeholder.placeholder

private const val SINGLE_IMAGE_SIZE = 76
private const val MAX_LINE_SIZE = 216

@Composable
fun ImageGrid(modifier: Modifier = Modifier, imageList: List<Image>) {
    val listLength = imageList.size
    val endPadding = if (listLength == 4) 64.dp else 32.dp

    if (imageList.size == 1) {
        SingleImageCell(
            modifier = modifier,
            imageUrl = imageList.first().url
        )
    } else {
        FlowRow(
            modifier = modifier.padding(end = endPadding),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            repeat(listLength) { imageIndex ->
                val painter = rememberImagePainter(data = imageList[imageIndex].url)
                BasicImageCell(painter = painter)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun SingleImageCell(modifier: Modifier = Modifier, imageUrl: String) {
    val (cellHeight, setCellHeight) = remember { mutableStateOf(SINGLE_IMAGE_SIZE.dp) }
    val (cellWidth, setCellWidth) = remember { mutableStateOf(SINGLE_IMAGE_SIZE.dp) }

    val painter = rememberImagePainter(
        data = imageUrl,
        onExecute = { _, current ->
            if (current.state is ImagePainter.State.Success) {
                val scale = if (current.size.height >= current.size.width) {
                    current.size.height / MAX_LINE_SIZE
                } else {
                    current.size.width / MAX_LINE_SIZE
                }
                val scaledHeight = current.size.height / scale
                val scaledWidth = current.size.width / scale
                setCellHeight(scaledHeight.dp)
                setCellWidth(scaledWidth.dp)
            }
            true
        }
    )

    BasicImageCell(modifier, painter, cellWidth, cellHeight)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun BasicImageCell(
    modifier: Modifier = Modifier,
    painter: ImagePainter,
    cellWidth: Dp = SINGLE_IMAGE_SIZE.dp,
    cellHeight: Dp = SINGLE_IMAGE_SIZE.dp
) {
    val isImageLoading = painter.state is ImagePainter.State.Loading

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        StatusIconSelector(painter.state)

        Image(
            painter = painter,
            contentDescription = "image",
            modifier = modifier
                .size(width = cellWidth, height = cellHeight)
                .placeholder(
                    visible = isImageLoading,
                    color = Color.LightGray
                ),
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalCoilApi
@Composable
fun StatusIconSelector(state: ImagePainter.State) {
    when (state) {
        is ImagePainter.State.Loading -> {
            CircularProgressIndicator(color = Color.White)
        }
        is ImagePainter.State.Error -> {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_error),
                contentDescription = "error",
                tint = Color.Red
            )
        }
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun ImageGridPreview() {
    WeChatMomentsTheme {
        ImageGrid(
            imageList = listOf(
                Image(url = "fake"),
                Image(url = "fake"),
                Image(url = "fake"),
                Image(url = "fake"),
                Image(url = "fake")
            )
        )
    }
}