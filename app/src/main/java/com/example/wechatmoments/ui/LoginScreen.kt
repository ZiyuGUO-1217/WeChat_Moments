package com.example.wechatmoments.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wechatmoments.LoginEvent
import com.example.wechatmoments.LoginViewModel
import com.example.wechatmoments.R
import com.example.wechatmoments.service.navigation.MomentScreenRoute
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> MomentScreenRoute.Moments.navigate(navHostController, event.userInfo)
            }
        }
    }

    Image(
        painter = painterResource(id = R.drawable.login),
        contentDescription = "login page",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}