package com.alexeyp.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TitleToolbar(
    toolbarState: TitleToolbarState,
    modifier: Modifier = Modifier,
    onActionClicked: (() -> Unit) = {}
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = toolbarState.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge.merge(),
            )

            if (toolbarState.actionType == TitleToolbarState.ActionType.ADD) {
                Spacer(modifier = Modifier.size(16.dp))
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = onActionClicked
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = toolbarState.title,
                    )
                }
            }
        }
    }
}

data class TitleToolbarState(
    val title: String,
    val actionType: ActionType,
    val modifier: Modifier = Modifier
) {
    enum class ActionType { ADD, NONE }
}