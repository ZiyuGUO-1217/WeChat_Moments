package com.example.wechatmoments.model

sealed interface MomentsAction {
    object RefreshMoments : MomentsAction
}
