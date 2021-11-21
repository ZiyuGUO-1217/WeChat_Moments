package com.example.wechatmoments.model

import java.net.URL

data class Tweet(
    val content: String,
    val userName: String,
    val images: List<URL> = emptyList(),
    val time: String
)
