package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wechatmoments.R
import com.example.wechatmoments.model.Image
import com.example.wechatmoments.model.Sender
import com.example.wechatmoments.model.Tweet
import com.example.wechatmoments.ui.theme.Shapes
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme

@Composable
fun BasicTweetCell(tweet: Tweet, time: String) {
    Column {
        CellContent(tweet, time)
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray.copy(alpha = 0.5f))
    }
}

@Composable
private fun CellContent(tweet: Tweet, time: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        ProfileImage(tweet.sender.avatar)
        TweetDetails(
            userName = tweet.sender.nickName,
            tweetContent = tweet.content,
            imageList = tweet.images,
            time = time
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ProfileImage(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            placeholder(R.drawable.defaultprofileimage)
        }
    )

    Image(
        painter = painter,
        contentDescription = "profile image",
        modifier = Modifier
            .size(48.dp)
            .clip(Shapes.medium),
    )
}

@Composable
private fun TweetDetails(
    userName: String,
    tweetContent: String,
    imageList: List<Image>,
    time: String
) {
    Column(modifier = Modifier.padding(start = 12.dp)) {
        UserName(userName)
        if (tweetContent.isNotBlank()) TweetContent(tweetContent)
        if (imageList.isNotEmpty()) Images(imageList)
        TimeAndMore(time)
    }
}

@Composable
private fun UserName(userName: String) {
    Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = userName,
        fontSize = 17.sp,
        color = Color(0XFF576B95),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun TweetContent(tweetContent: String) {
    MultiLineStateText(tweetContent)
}

@Composable
private fun Images(imageList: List<Image>) {
    if (imageList.size == 1) {
        SingleImageCell(imageUrl = imageList.first().url)
    } else {
        ImageGrid(imageList)
    }
    Spacer(modifier = Modifier.padding(bottom = 12.dp))
}

@Composable
private fun TimeAndMore(time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = time, color = Color.Gray)
        Box(
            modifier = Modifier
                .clip(Shapes.medium)
                .background(Color.LightGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 4.dp),
                painter = painterResource(id = R.drawable.ic_more_horiz),
                contentDescription = "more",
                tint = Color(0XFF576B95)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicTweetCellPreview() {
    WeChatMomentsTheme {
        BasicTweetCell(
            tweet = Tweet(
                content = "This is the first tweet",
                sender = Sender(
                    nickName = "Nick name",
                    userName = "User name"
                ),
                images = listOf(
                    Image(url = "fake"),
                    Image(url = "fake"),
                    Image(url = "fake"),
                    Image(url = "fake")
                )
            ),
            time = "1 day ago"
        )
    }
}