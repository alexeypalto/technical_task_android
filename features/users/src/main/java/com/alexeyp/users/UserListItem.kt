package com.alexeyp.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexeyp.network.model.User
import com.alexeyp.ui.theme.Blue
import com.alexeyp.users.utils.getTimeText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    user: User,
    onLongClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    user.id?.let { onLongClick.invoke(it) }
                }
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = stringResource(
                id = com.alexeyp.ui.R.string.format_two_string_with_comma,
                user.name,
                user.gender
            ),
            modifier = Modifier.padding(horizontal = 4.dp),
            fontSize = 17.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium.merge(),
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = stringResource(
                id = com.alexeyp.ui.R.string.format_two_string_with_comma,
                user.email,
                user.status
            ),
            modifier = Modifier.padding(horizontal = 4.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.displaySmall.merge(),
        )
        Spacer(modifier = Modifier.size(4.dp))
        user.creationDate?.let { dateTimeInMillis ->
            Text(
                text = stringResource(
                    id = R.string.hint_creation_time,
                    getTimeText(dateTimeInMillis)
                ),
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.displaySmall.merge(),
            )
        }
    }
}