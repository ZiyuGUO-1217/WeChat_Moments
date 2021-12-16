package com.example.wechatmoments.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wechatmoments.R
import com.example.wechatmoments.model.Comment
import com.example.wechatmoments.model.Sender
import com.example.wechatmoments.ui.theme.primaryblue
import com.example.wechatmoments.ui.weight.AnnotatedClickableText
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun TweetComments(likes: List<Sender>, comments: List<Comment>) {
    val commentsNumber = comments.size

    Column(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(Color.LightGray.copy(alpha = 0.25f))
            .padding(all = 4.dp)
            .animateContentSize()
    ) {
        if (likes.isNotEmpty()) LikesField(likes)
        if (likes.isNotEmpty() && comments.isNotEmpty()) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )
        }
        comments.forEachIndexed { index, comment ->
            TweetComment(comment)
            if (index + 1 != commentsNumber) Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun LikesField(likes: List<Sender>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        mainAxisAlignment = MainAxisAlignment.Center,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        lastLineMainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_likes),
            contentDescription = "like this tweet",
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(16.dp),
            tint = primaryblue
        )

        likes.forEach {
            Text(text = it.nickName, fontSize = 15.sp, color = primaryblue)
        }
    }
}

@Composable
private fun TweetComment(comment: Comment) {
    AnnotatedClickableText {
        appendAnnotatedText(text = comment.sender.nickName, annotation = "nick name") {
            Log.d("comments", "nick name clicked!")
        }
        appendPlainText(text = ": " + comment.content)
    }
}