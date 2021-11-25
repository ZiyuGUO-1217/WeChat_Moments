package com.example.wechatmoments.data

import com.example.wechatmoments.model.Tweet
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {
    fun createTweet(): List<Tweet> {
        return listOf(
//            Tweet(
//                userName = "User1",
//                content = "this is a tweet from user1",
//                images = listOf(URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")),
//                time = "5 minutes"
//            ),
//            Tweet(
//                userName = "User2",
//                content = "\nthis is a tweet from user2\n",
//                images = listOf(
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")
//                ),
//                time = "5 hours"
//            ),
//            Tweet(
//                userName = "User3",
//                content = "\nthis is\n a tweet from\n \t\t\tuser3",
//                images = listOf(
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")
//                ),
//                time = "1 days"
//            ),
//            Tweet(
//                userName = "User4",
//                content = "this\n is\n a\n tweet\n from\n user4",
//                images = listOf(
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")
//                ),
//                time = "1 days"
//            ),
//            Tweet(
//                userName = "User5",
//                content = "this\n is\n a\n tweet\n from\n user5\n Has 5 photos",
//                images = listOf(
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")
//                ),
//                time = "2 days"
//            ),
//            Tweet(
//                userName = "User6",
//                content = "this\n is\n a\n tweet\n from\n user6 \n which\n has\n 9 photos",
//                images = listOf(
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg"),
//                    URL("https://thoughtworks-mobile-2018.herokuapp.com/images/tweets/001.jpeg")
//                ),
//                time = "2 days"
//            )
        )
    }
}