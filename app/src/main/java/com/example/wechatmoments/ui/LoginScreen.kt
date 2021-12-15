package com.example.wechatmoments.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wechatmoments.LoginAction
import com.example.wechatmoments.LoginEvent
import com.example.wechatmoments.LoginViewModel
import com.example.wechatmoments.R
import com.example.wechatmoments.service.navigation.MomentScreenRoute
import com.example.wechatmoments.ui.weight.MomentsDialog
import com.example.wechatmoments.ui.weight.MomentsDialogState
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val actor = viewModel::dispatch
    val errorDialogState = remember { mutableStateOf(MomentsDialogState.HIDE) }
    UiEffects(viewModel, navHostController, errorDialogState)

    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "login page",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    MomentsDialog(
        dialogState = errorDialogState.value,
        onButtonClick = { actor(LoginAction.Retry) },
        onDismiss = { errorDialogState.value = MomentsDialogState.HIDE }
    )
}

@Composable
private fun UiEffects(
    viewModel: LoginViewModel,
    navHostController: NavHostController,
    errorDialogState: MutableState<MomentsDialogState>
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> MomentScreenRoute.Moments.navigate(navHostController, event.userInfo)
                is LoginEvent.LoginFailed -> errorDialogState.value = MomentsDialogState.SHOW
            }
        }
    }
}