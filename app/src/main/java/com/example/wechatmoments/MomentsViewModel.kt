package com.example.wechatmoments

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.model.MomentsEvent
import com.example.wechatmoments.model.MomentsState
import com.example.wechatmoments.model.UserInfo
import com.example.wechatmoments.service.navigation.MomentScreenRoute
import com.example.wechatmoments.service.network.onError
import com.example.wechatmoments.service.network.onSuccess
import com.example.wechatmoments.service.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MomentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MomentsRepository
) : BaseViewModel<MomentsState, MomentsAction>() {
    private val _events = MutableSharedFlow<MomentsEvent>()
    val event = _events.asSharedFlow()

    private val userName = "jsmith"

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
