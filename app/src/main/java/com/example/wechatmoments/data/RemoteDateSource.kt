package com.example.wechatmoments.data

import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDateSource {

    @GET("user/{userName}")
    suspend fun GetUserInfo(
        @Path("userName") userName: String
    )

    @GET("user/{userName}/tweets")
    suspend fun GetUserTweets(
        @Path("userName") userName: String
    )
}