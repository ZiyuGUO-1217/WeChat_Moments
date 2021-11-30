package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun ImageGrid(imageList: List<Image>) {
    val listLength = imageList.size
    val endPadding = if (listLength == 4) 72.dp else 48.dp

    FlowRow(
        modifier = Modifier.padding(end = endPadding),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        repeat(listLength) {
            ImageCell(imageUrl = imageList[it].url)
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
        modifier = modifier.size(72.dp),
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