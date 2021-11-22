package com.example.wechatmoments.data

import com.example.wechatmoments.model.Tweet
import javax.inject.Inject

class MomentsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDateSource: RemoteDateSource
) {
    fun loadLocalData(): List<Tweet> {
        return localDataSource.createTweet()
    }
}