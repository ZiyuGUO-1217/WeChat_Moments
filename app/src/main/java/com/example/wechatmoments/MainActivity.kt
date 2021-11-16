package com.example.wechatmoments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.wechatmoments.ui.MomentsScreen
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeChatMomentsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MomentsScreen()
                }
            }
        }
    }
}
