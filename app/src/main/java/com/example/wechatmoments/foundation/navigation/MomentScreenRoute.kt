package com.example.wechatmoments.foundation.navigation

import androidx.navigation.NavHostController
import com.example.wechatmoments.model.UserInfo

sealed interface MomentScreenRoute : ScreenRoute {
    object Login : MomentScreenRoute {
        override val route: String = "Login"
    }

    object Moments : MomentScreenRoute {
        const val KEY_PROFILE_IMAGE = "profileImage"
        const val KEY_AVATAR = "avatar"
        const val KEY_NICK = "nick"
        const val KEY_USER_NAME = "userName"

        override val route: String = "Moments/{$KEY_PROFILE_IMAGE}/{$KEY_AVATAR}/{$KEY_NICK}/{$KEY_USER_NAME}"

        fun navigate(navHostController: NavHostController, userInfo: UserInfo) {
            navigate(
                navHostController = navHostController,
                arguments = mapOf(
                    KEY_PROFILE_IMAGE to userInfo.profileImage,
                    KEY_AVATAR to userInfo.avatar,
                    KEY_NICK to userInfo.nickName,
                    KEY_USER_NAME to userInfo.userName
                )
            )
        }
    }
}
