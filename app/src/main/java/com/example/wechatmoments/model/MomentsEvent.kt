package com.example.wechatmoments.model

sealed interface MomentsEvent {
    object LoadSuccess : MomentsEvent
}
