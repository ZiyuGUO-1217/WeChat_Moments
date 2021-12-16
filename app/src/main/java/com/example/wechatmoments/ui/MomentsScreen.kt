package com.example.wechatmoments.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.wechatmoments.MomentsEvent
import com.example.wechatmoments.MomentsViewModel
import com.example.wechatmoments.R
import com.example.wechatmoments.model.Image
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.model.MomentsState
import com.example.wechatmoments.model.Sender
import com.example.wechatmoments.model.Tweet
import com.example.wechatmoments.model.UserInfo
import com.example.wechatmoments.service.viewmodel.collectAsState
import com.example.wechatmoments.ui.theme.Shapes
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.example.wechatmoments.ui.weight.ImageGrid
import com.example.wechatmoments.ui.weight.MomentsTopBar
import com.example.wechatmoments.ui.weight.MultiLineStateText
import com.example.wechatmoments.ui.weight.bringIntoViewAfterImeAnimation
import com.example.wechatmoments.ui.weight.clickableWithoutRipple
import com.example.wechatmoments.ui.weight.getStatusBarHeight
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect

private val MOMENTS_HEADER_HEIGHT = 320.dp
private val USER_AVATAR_SIZE = 80.dp
private val USER_PROFILE_BOTTOM_OFFSET = 28.dp
private val SWIPE_TO_REFRESH_THRESHOLD = 56.dp

private const val BASE_ALPHA_VALUE = 0.3f

val LocalMomentsActor = compositionLocalOf<(MomentsAction) -> Unit> { error("not provided") }

@Composable
fun MomentsScreen(viewModel: MomentsViewModel = hiltViewModel()) {
    val state by viewModel.collectAsState()
    val actor = viewModel::dispatch
    val lazyListState = rememberLazyListState()
    val swipeRefreshState = rememberSwipeRefreshState(state.isRefreshing)
    val commentInputFieldShowingState = remember { mutableStateOf(false) }

    UiEffects(viewModel, commentInputFieldShowingState)

    val closeInputField = { commentInputFieldShowingState.value = false }
    CompositionLocalProvider(LocalMomentsActor provides actor) {
        Box(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .clickableWithoutRipple(closeInputField),
        ) {
            MomentsScreenContent(state, lazyListState, swipeRefreshState)
            if (commentInputFieldShowingState.value) CommentInputField(closeInputField)
        }
    }
}

@Composable
private fun UiEffects(
    viewModel: MomentsViewModel,
    commentInputFieldShowingState: MutableState<Boolean>,
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is MomentsEvent.OpenInputField -> commentInputFieldShowingState.value = true
            }
        }
    }
}

@Composable
private fun MomentsScreenContent(
    state: MomentsState,
    lazyListState: LazyListState,
    swipeRefreshState: SwipeRefreshState
) {
    val actor = LocalMomentsActor.current

    val topBarAlpha = remember { mutableStateOf(0f) }
    val isHeaderVisible = lazyListState.firstVisibleItemIndex == 0
    topBarAlpha.value = calculateAlphaValue(lazyListState.firstVisibleItemScrollOffset, isHeaderVisible)

    SwipeRefresh(
        state = swipeRefreshState,
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(top = calculateTopPadding(swipeRefreshState.indicatorOffset)),
        swipeEnabled = state.isRefreshing.not(),
        refreshTriggerDistance = SWIPE_TO_REFRESH_THRESHOLD,
        indicatorPadding = PaddingValues(start = 32.dp),
        indicatorAlignment = Alignment.TopStart,
        onRefresh = { actor(MomentsAction.RefreshTweets) }
    ) {
        Box(modifier = Modifier.background(Color.White)) {
            LazyColumn(state = lazyListState) {
                item { UserProfile(state.userInfo, calculateContentHeight(swipeRefreshState.indicatorOffset)) }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                itemsIndexed(state.tweetList) { index, tweet ->
                    BasicTweetCell(index = index, userName = state.userInfo.userName, tweet = tweet)
                }
            }
        }
    }
    MomentsTopBar(alpha = topBarAlpha.value)
}

@Composable
private fun calculateTopPadding(indicatorOffset: Float): Dp {
    val density = LocalDensity.current
    return with(density) {
        maxOf(indicatorOffset.toDp() - SWIPE_TO_REFRESH_THRESHOLD, 0.dp)
    }
}

@Composable
private fun calculateContentHeight(indicatorOffset: Float): Dp {
    val density = LocalDensity.current
    return with(density) {
        MOMENTS_HEADER_HEIGHT + minOf(indicatorOffset.toDp(), SWIPE_TO_REFRESH_THRESHOLD)
    }
}

@Composable
private fun calculateAlphaValue(
    scrollOffset: Int,
    isItemVisible: Boolean
): Float {
    val density = LocalDensity.current
    with(density) {
        val threshold = (MOMENTS_HEADER_HEIGHT - USER_AVATAR_SIZE - getStatusBarHeight()).toPx()

        val scrollOffsetFromThreshold = scrollOffset - threshold
        return when {
            scrollOffsetFromThreshold in 0f..USER_PROFILE_BOTTOM_OFFSET.toPx() -> {
                scrollOffsetFromThreshold / USER_PROFILE_BOTTOM_OFFSET.toPx() * (1 - BASE_ALPHA_VALUE) + BASE_ALPHA_VALUE
            }
            isItemVisible && scrollOffsetFromThreshold < 0 -> 0f
            else -> 1f
        }
    }
}

@Composable
private fun UserProfile(userInfo: UserInfo, profileImageHeight: Dp) {
    Box(modifier = Modifier.fillMaxWidth()) {
        MomentsBackgroundImage(
            imageUrl = userInfo.profileImage,
            imageHeight = profileImageHeight
        )

        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userInfo.nickName,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 19.sp
            )
            AvatarImage(
                modifier = Modifier.padding(4.dp),
                imageUrl = userInfo.avatar,
                size = USER_AVATAR_SIZE
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MomentsBackgroundImage(imageUrl: String, imageHeight: Dp) {
    val painter = rememberImagePainter(data = imageUrl)

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
            .offset(y = -(USER_PROFILE_BOTTOM_OFFSET))
            .placeholder(
                visible = (painter.state is ImagePainter.State.Success).not(),
                color = Color.LightGray.copy(alpha = 0.25f)
            ),
        painter = painter,
        contentDescription = "profile image",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BasicTweetCell(index: Int, userName: String, tweet: Tweet) {
    Column {
        CellContent(index, tweet, userName)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewAfterImeAnimation(),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun CellContent(index: Int, tweet: Tweet, userName: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        AvatarImage(imageUrl = tweet.sender.avatar)
        TweetDetails(index, tweet, userName)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun AvatarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    size: Dp = 48.dp
) {
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
        modifier = modifier
            .size(size)
            .clip(Shapes.medium),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun TweetDetails(index: Int, tweet: Tweet, userName: String) {
    Column(modifier = Modifier.padding(start = 12.dp)) {
        UserName(tweet.sender.nickName)
        if (tweet.content.isNotBlank()) TweetContent(tweet.content)
        if (tweet.images.isNotEmpty()) ImageGrid(Modifier.padding(top = 8.dp), tweet.images)
        TimeAndMore(tweet, index, userName)
        if (tweet.comments.isNotEmpty() || tweet.likes.isNotEmpty()) TweetComments(tweet.likes, tweet.comments)
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
private fun TimeAndMore(tweet: Tweet, index: Int, userName: String) {
    val modifier = Modifier.padding(vertical = 8.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(end = 8.dp),
            text = tweet.sendTime,
            color = Color.Gray,
            fontSize = 13.sp
        )
        MoreMenu(index, tweet.containsUser(userName))
    }
}

@Preview(showBackground = true)
@Composable
fun BasicTweetCellPreview() {
    WeChatMomentsTheme {
        BasicTweetCell(
            index = 0,
            userName = "userName",
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
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MomentsScreenPreview() {
    WeChatMomentsTheme {
        MomentsScreen()
    }
}