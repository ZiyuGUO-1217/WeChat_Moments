package com.example.wechatmoments.service.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<S, A> : ViewModel() {
    abstract fun configureInitState(): S
    abstract fun dispatch(action: A)

    private val _flow by lazy {
        MutableStateFlow(configureInitState())
    }

    val flow by lazy {
        _flow.asSharedFlow()
    }

    private var state
        get() = _flow.value
        private set(value) {
            _flow.value = value
        }

    protected fun updateState(block: S.() -> S) {
        state = block(state)
    }
}

@Composable
fun <S, A> BaseViewModel<S, A>.collectAsState(): State<S> {
    return flow.collectAsState(initial = configureInitState())
}
