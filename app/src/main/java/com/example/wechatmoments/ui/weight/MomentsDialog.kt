package com.example.wechatmoments.ui.weight

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wechatmoments.ui.theme.Shapes
import com.example.wechatmoments.ui.theme.tertiaryBlue

private const val DIALOG_DEFAULT_FRACTION = 0.68f

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MomentsDialog(dialogState: MomentsDialogState, onButtonClick: () -> Unit, onDismiss: () -> Unit) {
    if (dialogState == MomentsDialogState.SHOW) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(DIALOG_DEFAULT_FRACTION),
                elevation = 20.dp,
                shape = Shapes.medium,
                backgroundColor = Color.White,
                contentColor = Color.Black
            ) {
                DialogContent(onDismiss, onButtonClick)
            }
        }
    }
}

@Composable
private fun DialogContent(onDismiss: () -> Unit, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Error",
            modifier = Modifier.padding(bottom = 16.dp),
            fontStyle = MaterialTheme.typography.h6.fontStyle,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = MaterialTheme.typography.h6.fontWeight
        )
        Text(
            text = "Ops, something went wrong! \nPlease try again",
            modifier = Modifier.padding(bottom = 20.dp),
            fontStyle = MaterialTheme.typography.body1.fontStyle,
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = MaterialTheme.typography.body1.fontWeight
        )

        Text(
            text = "RETRY",
            modifier = Modifier
                .clickable {
                    onDismiss()
                    onButtonClick()
                }
                .padding(4.dp)
                .align(Alignment.End),
            fontStyle = MaterialTheme.typography.body1.fontStyle,
            color = tertiaryBlue
        )
    }
}

enum class MomentsDialogState {
    HIDE,
    SHOW
}