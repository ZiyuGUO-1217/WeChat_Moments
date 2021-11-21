package com.example.wechatmoments

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.wechatmoments.data.MomentsRepository
import com.example.wechatmoments.model.MomentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MomentsViewModel @Inject constructor(
    private val momentsRepository: MomentsRepository
) : ViewModel() {
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
        val tweetList = momentsRepository.loadLocalData()
        updateState { copy(tweetList = tweetList) }
    }
}

@Composable
fun MomentsViewModel.collectAsState(): State<MomentsState> {
    return flow.collectAsState(initial = state)
}