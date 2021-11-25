package com.example.wechatmoments.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo(
    @SerializedName("profile-image") val profileImage: String = "",
    val avatar: String = "",
    @SerializedName("nick") val nickName: String = "",
    @SerializedName("username") val userName: String = ""
) : Serializable