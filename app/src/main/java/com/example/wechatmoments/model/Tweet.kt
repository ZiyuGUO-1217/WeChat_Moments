package com.example.wechatmoments.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tweet(
    val content: String = "",
    val images: List<Image> = emptyList(),
    val sender: Sender = Sender(),
    val comments: List<Comment> = emptyList(),
    val likes: List<Sender> = emptyList(),
    val sendTime: String = "1 hour ago"
) : Serializable {

    fun containsUser(userName: String): Boolean {
         return likes.map { it.userName }.contains(userName)
    }
}

data class Comment(
    val content: String,
    val sender: Sender
) : Serializable

data class Image(
    val url: String
) : Serializable

data class Sender(
    @SerializedName("username") val userName: String = "",
    @SerializedName("nick") val nickName: String = "",
    val avatar: String = ""
) : Serializable