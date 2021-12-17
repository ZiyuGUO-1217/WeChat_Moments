package com.example.wechatmoments.ui.weight

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wechatmoments.model.MomentsAction
import com.example.wechatmoments.ui.LocalMomentsActor
import com.example.wechatmoments.ui.theme.Shapes
import kotlinx.coroutines.delay

@Composable
fun BoxScope.CommentInputField(closeInputField: () -> Unit) {
    val actor = LocalMomentsActor.current

    val inputComment = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .background(Color(0xFFEEEEEE))
            .animateContentSize()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            value = inputComment.value,
            onValueChange = { inputComment.value = it },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.body1,
            placeholder = { Text(text = "Leave comment", color = Color.LightGray.copy(alpha = 0.5f)) },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            shape = Shapes.medium
        )
        Button(
            onClick = {
                actor(MomentsAction.LeaveComment(inputComment.value))
                closeInputField()
                inputComment.value = ""
            },
            enabled = inputComment.value.isNotEmpty(),
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text(text = "Send")
        }
    }

    LaunchedEffect(focusRequester) {
        delay(100L)
        focusRequester.requestFocus()
    }
}