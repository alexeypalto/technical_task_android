package com.alexeyp.ui.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.alexeyp.ui.R

@Composable
fun ErrorUI(error: Throwable) {
    Text(
        text = error.message ?: error.cause?.message ?: stringResource(id = R.string.common_error),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ErrorUI(message: String) {
    ErrorUI(error = Throwable(message))
}