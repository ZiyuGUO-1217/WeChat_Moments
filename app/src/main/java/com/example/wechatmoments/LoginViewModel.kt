package com.example.wechatmoments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.model.UserInfo
import com.example.wechatmoments.service.network.onError
import com.example.wechatmoments.service.network.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface LoginEvent {
    data class LoginSuccess(val userInfo: UserInfo) : LoginEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MomentsRepository
) : ViewModel() {
    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    private val userName = "jsmith"

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo(userName)
                .onSuccess { userInfo ->
                    _events.emit(LoginEvent.LoginSuccess(userInfo))
                }
                .onError {
                    Log.d("userInfo: error", "$it")
                }
        }
    }

}