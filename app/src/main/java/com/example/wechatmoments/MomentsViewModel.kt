package com.example.wechatmoments

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.foundation.navigation.MomentScreenRoute
import com.example.wechatmoments.foundation.network.onError
import com.example.wechatmoments.foundation.network.onSuccess
import com.example.wechatmoments.foundation.viewmodel.BaseViewModel
import com.example.wechatmoments.model.Comment
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.model.MomentsState
import com.example.wechatmoments.model.Sender
import com.example.wechatmoments.model.Tweet
import com.example.wechatmoments.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface MomentsEvent {
    object OpenInputField : MomentsEvent
}

@HiltViewModel
class MomentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MomentsRepository
) : BaseViewModel<MomentsState, MomentsAction>() {
    private val _events = MutableSharedFlow<MomentsEvent>()
    val event = _events.asSharedFlow()

    private val userName = "jsmith"
    private var targetTweetIndex: Int = -1

    override fun configureInitState(): MomentsState {
        val profileImage = savedStateHandle.get<String>(MomentScreenRoute.Moments.KEY_PROFILE_IMAGE)!!
        val avatar = savedStateHandle.get<String>(MomentScreenRoute.Moments.KEY_AVATAR)!!
        val nickName = savedStateHandle.get<String>(MomentScreenRoute.Moments.KEY_NICK)!!
        val userName = savedStateHandle.get<String>(MomentScreenRoute.Moments.KEY_USER_NAME)!!
        return MomentsState(
            userInfo = UserInfo(profileImage, avatar, nickName, userName)
        )
    }

    init {
        loadTweetsList()
    }

    override fun dispatch(action: MomentsAction) {
        when (action) {
            MomentsAction.RefreshTweets -> loadTweetsList()
            is MomentsAction.LikeAction -> handleLikeAction(action.index)
            is MomentsAction.OpenCommentInputField -> openCommentInputField(action.index)
            is MomentsAction.LeaveComment -> leaveComment(action.comment)
        }
    }

    private fun leaveComment(commentContent: String) {
        if (targetTweetIndex >= 0) {
            val tweet = state.tweetList[targetTweetIndex]
            val sender = with(state.userInfo) {
                Sender(userName = userName, nickName = nickName, avatar = avatar)
            }
            val comments = tweet.comments.toMutableList().apply {
                add(Comment(content = commentContent, sender = sender))
            }
            val tweetList = state.tweetList.toMutableList().apply {
                set(targetTweetIndex, tweet.copy(comments = comments))
            }

            updateState {
                copy(tweetList = tweetList)
            }
        }
    }

    private fun openCommentInputField(index: Int) {
        targetTweetIndex = index
        viewModelScope.launch {
            _events.emit(MomentsEvent.OpenInputField)
        }
    }

    private fun handleLikeAction(index: Int) {
        val tweet = state.tweetList[index]
        if (tweet.containsUser(state.userInfo.userName)) {
            cancelLikeTweet(index, tweet)
        } else {
            likeTweet(index, tweet)
        }
    }

    private fun cancelLikeTweet(index: Int, tweet: Tweet) {
        val likesList = tweet.likes.toMutableList().apply {
            remove(first { it.userName == state.userInfo.userName })
        }
        updateTweetLikesList(index, tweet, likesList)
    }

    private fun likeTweet(index: Int, tweet: Tweet) {
        val sender = with(state.userInfo) {
            Sender(userName = userName, nickName = nickName, avatar = avatar)
        }
        val likesList = tweet.likes.toMutableList().apply {
            add(sender)
        }
        updateTweetLikesList(index, tweet, likesList)
    }

    private fun updateTweetLikesList(
        index: Int,
        tweet: Tweet,
        likesList: List<Sender>
    ) {
        val tweetList = state.tweetList.toMutableList().apply {
            set(index, tweet.copy(likes = likesList))
        }

        updateState {
            copy(tweetList = tweetList)
        }
    }

    private fun loadTweetsList() {
        updateState { copy(isRefreshing = true) }
        viewModelScope.launch {
            repository.getUserTweets(userName)
                .onSuccess { tweetsList ->
                    val validTweetsList = tweetsList
                        .filter { it.content.isNotBlank() || it.images.isNotEmpty() }

                    updateState {
                        copy(tweetList = validTweetsList)
                    }
                }
                .onError {
                    Log.d("userInfo: error", "$it")
                }
            updateState { copy(isRefreshing = false) }
        }
    }
}
