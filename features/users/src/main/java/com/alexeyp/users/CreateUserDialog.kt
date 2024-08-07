package com.alexeyp.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alexeyp.network.model.User
import com.alexeyp.ui.compose.PrimaryButton
import com.alexeyp.ui.theme.SlideTestTheme

@Composable
fun CreateUserDialog(
    modifier: Modifier = Modifier,
    onUserCreated: (User) -> Unit,
    onDismiss: () -> Unit
) {
    val genderOptions = listOf(
        stringResource(id = R.string.hint_gender_male),
        stringResource(id = R.string.hint_gender_female)
    )
    val (selectedGenderOption, onOptionSelected) = remember { mutableStateOf(genderOptions[0]) }
    val checkedStatusState = remember { mutableStateOf(true) }
    var currentNameInput by remember { mutableStateOf(TextFieldValue(String())) }
    var currentEmailInput by remember { mutableStateOf(TextFieldValue(String())) }

    val context = LocalContext.current

    var isValid by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.title_create_user_dialog),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.merge(),
                )
                Spacer(Modifier.size(20.dp))

                Text(
                    stringResource(id = R.string.hint_name),
                    style = MaterialTheme.typography.labelSmall.merge(),
                )
                Spacer(modifier = Modifier.size(8.dp))
                TextField(
                    currentNameInput,
                    onValueChange = {
                        // check on change, if the value is valid
//            isValid = onCheck(it.text)
                        currentNameInput = it
                    },
                    colors = androidx.compose.material3.TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(Modifier.size(16.dp))

                Text(
                    stringResource(id = R.string.hint_email),
                    style = MaterialTheme.typography.labelSmall.merge(),
                )
                Spacer(modifier = Modifier.size(8.dp))
                TextField(
                    value = currentEmailInput,
                    onValueChange = {
                        // check on change, if the value is valid
//            isValid = onCheck(it.text)
                        currentEmailInput = it
                    },
                    colors = androidx.compose.material3.TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(Modifier.size(16.dp))

                Text(
                    stringResource(id = R.string.hint_status),
                    style = MaterialTheme.typography.labelSmall.merge(),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = checkedStatusState.value,
                        onCheckedChange = {
                            checkedStatusState.value = it
                        }
                    )
                    Text(
                        text = if (checkedStatusState.value) stringResource(id = R.string.hint_status_active) else stringResource(
                            id = R.string.hint_status_inactive
                        ),
                        style = MaterialTheme.typography.bodyMedium.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(Modifier.size(16.dp))

                Text(
                    stringResource(id = R.string.hint_gender),
                    style = MaterialTheme.typography.labelSmall.merge(),
                )
                genderOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedGenderOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedGenderOption),
                            onClick = { onOptionSelected(text) }
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium.merge(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(Modifier.size(20.dp))

                PrimaryButton(text = stringResource(id = R.string.action_create_user_dialog)) {
                    val user = User(
                        id = null,
                        name = currentNameInput.text,
                        gender = selectedGenderOption.lowercase(),
                        status = if (checkedStatusState.value)
                            context.getString(R.string.hint_status_active).lowercase()
                        else
                            context.getString(R.string.hint_status_inactive).lowercase(),
                        email = currentEmailInput.text
                    )
                    onUserCreated.invoke(user)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCreateUserDialog() {
    SlideTestTheme {
        CreateUserDialog(
            modifier = Modifier,
            onDismiss = {
            },
            onUserCreated = {
            })
    }
}
