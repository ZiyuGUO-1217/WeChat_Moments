package com.example.wechatmoments

import com.example.wechatmoments.Model.Tweet

class LocalDataSource {
    fun createTweet(): List<Tweet>{
        return listOf(
            Tweet(
                userName = "",
                tweetContent = "",
                time = "5 minutes"
            ),
            Tweet(
                userName = "",
                tweetContent = "",
                time = "5 minutes"
            ),
            Tweet(
                userName = "",
                tweetContent = "",
                time = "5 minutes"
            ),
            Tweet(
                userName = "",
                tweetContent = "",
                time = "5 minutes"
            )
        )
    }
}