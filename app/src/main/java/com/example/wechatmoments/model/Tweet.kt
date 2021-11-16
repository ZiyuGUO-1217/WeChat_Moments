package com.example.wechatmoments.model

data class Tweet(
    val userName: String,
    val tweetContent: String,
    val imageList: Int = 0,
    val time: String
)
