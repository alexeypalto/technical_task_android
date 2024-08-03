package com.alexeyp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexeyp.ui.theme.SlideTestTheme

@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String,
    negativeButtonText: String? = null,
    positiveButtonText: String,
    onNegativeClick: (() -> Unit)? = null,
    onPositiveClick: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        backgroundColor = MaterialTheme.colorScheme.background,
        title = {
            title?.let {
                Text(
                    text = it,
                )
            }
        },
        text = {
            Text(
                text = text
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                negativeButtonText?.let {
                    TextButton(
                        onClick = {
                            onNegativeClick?.invoke()
                            onDismissRequest.invoke()
                        }
                    ) {
                        Text(
                            text = negativeButtonText.uppercase(),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                TextButton(
                    onClick = {
                        onPositiveClick?.invoke()
                        onDismissRequest.invoke()
                    }
                ) {
                    Text(
                        text = positiveButtonText.uppercase(),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
    )

}


@Preview
@Composable
fun DialogPreview() {
    SlideTestTheme {
        Dialog(
            onDismissRequest = {},
            title = "Dialog title",
            text = "Dialog text",
            negativeButtonText = "Cancel",
            positiveButtonText = "OK",
        )
    }
}