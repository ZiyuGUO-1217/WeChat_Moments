package com.example.wechatmoments.model

sealed interface MomentsAction {
    object RefreshTweets : MomentsAction

    data class LikeAction(val index: Int) : MomentsAction
    data class OpenCommentInputField(val index: Int) : MomentsAction
    data class LeaveComment(val comment: String) : MomentsAction
}
