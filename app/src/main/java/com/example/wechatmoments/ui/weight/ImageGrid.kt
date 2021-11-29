package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.wechatmoments.R
import com.example.wechatmoments.model.Image
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrid(imageList: List<Image>) {
    val listLength = imageList.size
    if (listLength == 1) {
        ImageCell(
            modifier = Modifier
                .heightIn(min = 72.dp, max = 216.dp)
                .widthIn(min = 72.dp, max = 216.dp),
            imageUrl = imageList.first().url
        )
    } else {
        val endPadding = if (listLength == 4) 72.dp else 48.dp

        FlowRow(
            modifier = Modifier.padding(end = endPadding),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            repeat(listLength) {
                ImageCell(
                    modifier = Modifier.size(72.dp),
                    imageUrl = imageList[it].url
                )
            }
        }
    }
}

@Composable
private fun ImageCell(modifier: Modifier = Modifier, imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            placeholder(R.drawable.defaultprofileimage)
        }
    )

    Image(
        painter = painter,
        contentDescription = "image",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
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