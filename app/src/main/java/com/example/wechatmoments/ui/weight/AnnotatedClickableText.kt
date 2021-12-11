package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.wechatmoments.service.utils.MomentsAnnotationString

@Composable
fun AnnotatedClickableText(
    modifier: Modifier = Modifier,
    builder: @Composable MomentsAnnotationString.Builder.() -> Unit
) {
    val myAnnotationString = MomentsAnnotationString().Builder()
        .apply { builder() }
        .build()

    val text = myAnnotationString.text
    val annotatedTexts = myAnnotationString.annotatedTexts

    ClickableText(
        text = text,
        modifier = modifier.fillMaxWidth(),
        style = TextStyle(fontSize = 15.sp),
        onClick = { offset ->
            annotatedTexts.forEach { annotatedText ->
                text.getStringAnnotations(annotatedText.tag, offset, offset)
                    .firstOrNull()?.let {
                        annotatedText.onClick()
                    }
            }
        }
    )
}