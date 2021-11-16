package com.example.wechatmoments.data

import com.example.wechatmoments.model.Tweet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {
    fun createTweet(): List<Tweet> {
        return listOf(
            Tweet(
                userName = "User1",
                tweetContent = "this is a tweet from user1",
                imageList = 1,
                time = "5 minutes"
            ),
            Tweet(
                userName = "User2",
                tweetContent = "\nthis is a tweet from user2\n",
                imageList = 2,
                time = "5 hours"
            ),
            Tweet(
                userName = "User3",
                tweetContent = "\nthis is\n a tweet from\n \t\t\tuser3",
                imageList = 3,
                time = "1 days"
            ),
            Tweet(
                userName = "User4",
                tweetContent = "this\n is\n a\n tweet\n from\n user4",
                imageList = 4,
                time = "1 days"
            ),
            Tweet(
                userName = "User5",
                tweetContent = "this\n is\n a\n tweet\n from\n user5\n Has 5 photos",
                imageList = 5,
                time = "2 days"
            ),
            Tweet(
                userName = "User6",
                tweetContent = "this\n is\n a\n tweet\n from\n user6 \n which\n has\n 9 photos",
                imageList = 9,
                time = "2 days"
            )
        )
    }
}