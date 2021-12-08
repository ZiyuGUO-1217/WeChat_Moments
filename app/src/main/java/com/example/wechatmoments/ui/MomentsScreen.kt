package com.example.wechatmoments.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.wechatmoments.MomentsViewModel
import com.example.wechatmoments.R
import com.example.wechatmoments.collectAsState
import com.example.wechatmoments.model.Comment
import com.example.wechatmoments.model.Image
import com.example.wechatmoments.model.Sender
import com.example.wechatmoments.model.Tweet
import com.example.wechatmoments.ui.theme.Shapes
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.example.wechatmoments.ui.weight.AnnotatedClickableText
import com.example.wechatmoments.ui.weight.ImageGrid
import com.example.wechatmoments.ui.weight.MultiLineStateText

@Composable
fun MomentsScreen(viewModel: MomentsViewModel = hiltViewModel()) {
    val state by viewModel.collectAsState()

    LazyColumn {
        items(state.tweetList) {
            BasicTweetCell(
                tweet = it,
                time = "1 minute ago"
            )
        }
    }
}

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
            .padding(top = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        ProfileImage(tweet.sender.avatar)
        TweetDetails(
            userName = tweet.sender.nickName,
            tweetContent = tweet.content,
            imageList = tweet.images,
            comments = tweet.comments,
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
            error(R.drawable.defaultprofileimage)
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
    comments: List<Comment>,
    time: String
) {
    Column(modifier = Modifier.padding(start = 12.dp)) {
        UserName(userName)
        if (tweetContent.isNotBlank()) TweetContent(tweetContent)
        if (imageList.isNotEmpty()) ImageGrid(Modifier.padding(top = 8.dp), imageList)
        TimeAndMore(time)
        if (comments.isNotEmpty()) TweetComments(comments)
    }
}

@Composable
private fun UserName(userName: String) {
    Text(
        modifier = Modifier,
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
fun TweetComments(comments: List<Comment>) {
    val commentsNumber = comments.size

    Column(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(Color.LightGray.copy(alpha = 0.25f))
            .padding(all = 4.dp)
    ) {
        comments.forEachIndexed { index, comment ->
            TweetComment(comment)
            if (index + 1 != commentsNumber) Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TweetComment(comment: Comment) {
    AnnotatedClickableText {
        appendAnnotatedText(text = comment.sender.nickName, annotation = "nick name") {
            Log.d("comments", "nick name clicked!")
        }
        appendPlainText(text = ": " + comment.content)
    }
}

@Composable
private fun TimeAndMore(time: String) {
    val modifier = Modifier.padding(vertical = 8.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(end = 8.dp),
            text = time,
            color = Color.Gray,
            fontSize = 13.sp
        )
        MoreMenu()
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

@Preview(showBackground = true)
@Composable
fun MomentsScreenPreview() {
    WeChatMomentsTheme {
        MomentsScreen(viewModel())
    }
}