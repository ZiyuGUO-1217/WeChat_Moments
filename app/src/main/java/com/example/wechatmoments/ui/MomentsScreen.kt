package com.example.wechatmoments.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme

@Composable
fun MomentsScreen(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun MomentsScreenPreview() {
    WeChatMomentsTheme {
        MomentsScreen("Android")
    }
}