package com.example.wechatmoments

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.model.MomentsState
import com.example.wechatmoments.network.onError
import com.example.wechatmoments.network.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MomentsViewModel @Inject constructor(
    private val repository: MomentsRepository
) : ViewModel() {
    private val userName = "jsmith"

    private val _flow by lazy {
        MutableStateFlow(MomentsState())
    }

    val flow by lazy {
        _flow.asSharedFlow()
    }

    var state
        get() = _flow.value
        private set(value) {
            _flow.value = value
        }

    private fun updateState(block: MomentsState.() -> MomentsState) {
        state = block(state)
    }

    init {
        loadUserInfo()
        loadTweetsList()
    }

    private fun loadTweetsList() {
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

@Composable
fun MomentsViewModel.collectAsState(): State<MomentsState> {
    return flow.collectAsState(initial = state)
}