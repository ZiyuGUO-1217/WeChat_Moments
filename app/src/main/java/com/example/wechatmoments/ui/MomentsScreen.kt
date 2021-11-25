package com.example.wechatmoments.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wechatmoments.MomentsViewModel
import com.example.wechatmoments.collectAsState
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.example.wechatmoments.ui.weight.BasicTweetCell

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

@Preview(showBackground = true)
@Composable
fun MomentsScreenPreview() {
    WeChatMomentsTheme {
        MomentsScreen(viewModel())
    }
}