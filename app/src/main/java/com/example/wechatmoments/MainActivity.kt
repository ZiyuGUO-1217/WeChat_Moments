package com.example.wechatmoments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.example.wechatmoments.ui.MomentsScreen
import com.example.wechatmoments.ui.theme.WeChatMomentsTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = isSystemInDarkTheme().not()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons,
                    transformColorForLightContent = { it }
                )
            }

            WeChatMomentsTheme {
                ProvideWindowInsets {
                    Content()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        MomentsScreen()
    }
}
