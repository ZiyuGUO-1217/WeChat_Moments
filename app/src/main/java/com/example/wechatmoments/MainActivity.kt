package com.example.wechatmoments

import android.os.Build
import android.os.Bundle
import android.view.View
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
        setActivityFullScreen()

        setContent {
            SetTransparentStatusBar()
            WeChatMomentsTheme {
                ProvideWindowInsets {
                    Content()
                }
            }
        }
    }

    @Composable
    private fun SetTransparentStatusBar() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = isSystemInDarkTheme().not()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons,
                transformColorForLightContent = { it }
            )
        }
    }

    private fun setActivityFullScreen() {
        window.decorView.apply {
            var flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            systemUiVisibility = flags
        }
    }

    @Composable
    private fun Content() {
        MomentsScreen()
    }
}
