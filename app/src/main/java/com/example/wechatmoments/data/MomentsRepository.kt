package com.example.wechatmoments.data

import com.example.wechatmoments.foundation.network.ApiResult
import com.example.wechatmoments.foundation.network.callApi
import com.example.wechatmoments.model.Tweet
import com.example.wechatmoments.model.UserInfo
import javax.inject.Inject

class MomentsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDateSource: RemoteDateSource
) {
    fun loadLocalData(): List<Tweet> {
        return localDataSource.createTweet()
    }

    suspend fun getUserInfo(userName: String): ApiResult<UserInfo> = callApi {
        val userInfo = remoteDateSource.getUserInfo(userName)
        return ApiResult.Success(userInfo)
    }

    suspend fun getUserTweets(userName: String): ApiResult<List<Tweet>> = callApi {
        val userTweets = remoteDateSource.getUserTweets(userName)
        return ApiResult.Success(userTweets)
    }
}