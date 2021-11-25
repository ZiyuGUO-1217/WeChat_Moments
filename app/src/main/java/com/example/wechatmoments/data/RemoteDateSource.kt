package com.example.wechatmoments.data

import com.example.wechatmoments.model.UserInfo
import com.example.wechatmoments.model.Tweet
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDateSource {

    @GET("user/{userName}")
    suspend fun getUserInfo(
        @Path("userName") userName: String
    ): UserInfo

    @GET("user/{userName}/tweets")
    suspend fun getUserTweets(
        @Path("userName") userName: String
    ): List<Tweet>
}