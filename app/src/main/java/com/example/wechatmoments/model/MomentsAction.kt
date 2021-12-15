package com.example.wechatmoments.model

sealed interface MomentsAction {
    object RefreshTweets : MomentsAction

    data class LikeAction(val index: Int): MomentsAction
}
