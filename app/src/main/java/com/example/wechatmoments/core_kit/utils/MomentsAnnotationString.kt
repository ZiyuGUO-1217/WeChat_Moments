package com.example.wechatmoments.core_kit.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import java.util.UUID

class MomentsAnnotationString {
    private var _text = AnnotatedString(text = "")
    val text
        get() = _text

    private val _annotatedTexts: MutableList<AnnotatedText> = mutableListOf()
    val annotatedTexts: List<AnnotatedText>
        get() = _annotatedTexts

    inner class Builder {
        private val _builder = AnnotatedString.Builder()

        @Composable
        fun appendPlainText(text: String): Builder {
            _builder.withStyle(style = getPlainTextStyle()) {
                append(text)
            }

            return this
        }

        @Composable
        fun appendAnnotatedText(
            text: String,
            annotation: String,
            onClick: () -> Unit
        ): Builder {
            val tag = UUID.randomUUID().toString()
            val annotatedText = AnnotatedText(text, tag, annotation, onClick)
            _annotatedTexts.add(annotatedText)

            with(_builder) {
                pushStringAnnotation(tag, annotation)
                withStyle(style = getAnnotatedTextStyle()) {
                    append(text)
                }
                pop()
            }

            return this
        }

        fun build(): MomentsAnnotationString {
            return this@MomentsAnnotationString.apply {
                _text = _builder.toAnnotatedString()
            }
        }
    }

    companion object {
        @Composable
        private fun getPlainTextStyle() = MaterialTheme.typography.body1
            .copy(fontSize = 15.sp)
            .toSpanStyle()

        @Composable
        private fun getAnnotatedTextStyle() = MaterialTheme.typography.body1
            .copy(
                fontSize = 15.sp,
                color = Color(0XFF576B95)
            )
            .toSpanStyle()
    }
}

data class AnnotatedText(
    val text: String,
    val tag: String,
    val annotation: String,
    val onClick: () -> Unit
)