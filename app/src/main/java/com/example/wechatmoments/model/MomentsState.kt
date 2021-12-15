package com.example.wechatmoments.model

data class MomentsState(
    val userInfo: UserInfo = UserInfo(),
    val tweetList: List<Tweet> = emptyList(),
    val isRefreshing: Boolean = false
)
