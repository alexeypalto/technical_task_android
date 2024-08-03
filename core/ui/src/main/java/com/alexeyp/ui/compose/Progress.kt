package com.alexeyp.ui.compose

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenProgress() {
    CircularProgressIndicator(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
          .wrapContentSize(Alignment.Center),
        color = MaterialTheme.colorScheme.onPrimary,
        strokeWidth = 4.dp
    )
}

@Composable
fun PageLoadingProgress() {
    CircularProgressIndicator(
        modifier = Modifier
          .fillMaxWidth()
          .wrapContentWidth(Alignment.CenterHorizontally),
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun HorizontalProgress() {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth().height(2.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    )
}
