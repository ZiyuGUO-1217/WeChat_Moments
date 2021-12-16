package com.example.wechatmoments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.foundation.network.onError
import com.example.wechatmoments.foundation.network.onSuccess
import com.example.wechatmoments.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed interface LoginEvent {
    data class LoginSuccess(val userInfo: UserInfo) : LoginEvent
    object LoginFailed : LoginEvent
}

sealed class LoginAction {
    object Retry : LoginAction()
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

    fun dispatch(action: LoginAction) {
        when (action) {
            LoginAction.Retry -> loadUserInfo()
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo(userName)
                .onSuccess { userInfo ->
                    _events.emit(LoginEvent.LoginSuccess(userInfo))
                }
                .onError {
                    _events.emit(LoginEvent.LoginFailed)
                }
        }
    }
}