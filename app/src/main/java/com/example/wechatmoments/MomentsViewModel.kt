package com.example.wechatmoments

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.model.MomentsState
import com.example.wechatmoments.service.network.onError
import com.example.wechatmoments.service.network.onSuccess
import com.example.wechatmoments.service.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MomentsViewModel @Inject constructor(
    private val repository: MomentsRepository
) : BaseViewModel<MomentsState, MomentsAction>() {
    private val userName = "jsmith"

    override fun configureInitState(): MomentsState {
        return MomentsState()
    }

    init {
        loadUserInfo()
        loadTweetsList()
    }

    override fun dispatch(action: MomentsAction) {
        when (action) {
            MomentsAction.RefreshMoments -> loadTweetsList()
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

    private fun loadUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo(userName)
                .onSuccess { userInfo ->
                    updateState {
                        copy(userInfo = userInfo)
                    }
                }
                .onError {
                    Log.d("userInfo: error", "$it")
                }
        }
    }
}
