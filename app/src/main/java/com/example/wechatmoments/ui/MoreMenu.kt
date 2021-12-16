package com.example.wechatmoments.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wechatmoments.R
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.ui.theme.Shapes
import com.example.wechatmoments.ui.theme.primaryblue
import com.example.wechatmoments.ui.weight.VerticalDivider
import com.example.wechatmoments.ui.weight.clickableWithoutRipple

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.MoreMenu(index: Int, hasLiked: Boolean) {
    val actor = LocalMomentsActor.current
    var interactionVisible by remember { mutableStateOf(false) }
    val onClick = { interactionVisible = !interactionVisible }
    val onLikesClick = {
        actor(MomentsAction.LikeAction(index))
        interactionVisible = false
    }
    val onCommentsClick = {
        actor(MomentsAction.OpenCommentInputField(index))
        interactionVisible = false
    }
    val likesButtonText = if (hasLiked) {
        stringResource(R.string.moments_tweet_menu_likes_cancel)
    } else {
        stringResource(R.string.moments_tweet_menu_likes)
    }

    Row(
        modifier = Modifier
            .weight(1f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            modifier = Modifier.clipToBounds(),
            visible = interactionVisible,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            InteractionMenu(onLikesClick, onCommentsClick, likesButtonText)
        }
        MoreButton(onClick)
    }
}

@Composable
private fun InteractionMenu(onLikesClick: () -> Unit, onCommentsClick: () -> Unit, likesButtonText: String) {
    Card(
        modifier = Modifier.fillMaxHeight(),
        backgroundColor = Color.DarkGray,
        border = null,
        shape = Shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconText(
                iconId = R.drawable.ic_outline_likes,
                text = likesButtonText,
                onClick = onLikesClick
            )
            VerticalDivider(Modifier.padding(horizontal = 12.dp))
            IconText(
                iconId = R.drawable.ic_outline_comments,
                text = stringResource(R.string.moments_tweet_menu_comments),
                onClick = onCommentsClick
            )
        }
    }
}

@Composable
private fun IconText(iconId: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickableWithoutRipple { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(12.dp),
            painter = painterResource(id = iconId),
            contentDescription = text,
            tint = Color.White
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }

}

@Composable
private fun MoreButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(Shapes.medium)
            .background(Color.LightGray.copy(alpha = 0.25f))
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 4.dp),
            painter = painterResource(id = R.drawable.ic_more_horiz),
            contentDescription = "more",
            tint = primaryblue
        )
    }
}
