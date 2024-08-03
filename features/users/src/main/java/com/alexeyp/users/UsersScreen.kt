package com.alexeyp.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexeyp.ui.compose.Dialog
import com.alexeyp.ui.compose.ErrorUI
import com.alexeyp.ui.compose.HorizontalProgress
import com.alexeyp.ui.compose.TitleToolbar
import com.alexeyp.ui.compose.TitleToolbarState

@Composable
internal fun UsersRoute(
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel(),
) {
    UsersScreen(
        modifier,
        onShowSnackbar,
        viewModel
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UsersScreen(
    modifier: Modifier,
    onShowSnackbar: (String) -> Unit,
    viewModel: UsersViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val shouldDisplayError = viewModel.shouldDisplayError
    var deleteUserDialogOpen by rememberSaveable { mutableStateOf<Pair<Boolean, Long?>>(false to null) }
    var createUserDialogOpen by rememberSaveable { mutableStateOf(false) }

    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh = {
        refreshing = true
        viewModel.refresh()
    })

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    LaunchedEffect(shouldDisplayError.first) {
        if (shouldDisplayError.first) {
            onShowSnackbar(shouldDisplayError.second)
            viewModel.clearErrorState()
        }
    }

    if (deleteUserDialogOpen.first) {
        DeleteConfirmationDialog(
            onDismissRequest = {
                deleteUserDialogOpen = false to null
            },
            onAccept = {
                deleteUserDialogOpen.second?.let { viewModel.deleteUser(it) }
                deleteUserDialogOpen = false to null
            }
        )
    }
    if (createUserDialogOpen) {
        CreateUserDialog(onUserCreated = {
            createUserDialogOpen = false
            viewModel.createUser(it)
        }) {
            createUserDialogOpen = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pullRefresh(pullRefreshState),
    ) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        TitleToolbar(
            toolbarState = TitleToolbarState(
                title = stringResource(id = R.string.title_users),
                actionType = com.alexeyp.ui.compose.TitleToolbarState.ActionType.ADD,
                modifier = modifier
            ),
            onActionClicked = {
                createUserDialogOpen = true
            }
        )

        when (uiState) {
            is UsersUiState.Error -> {
                refreshing = false
                ErrorUI(
                    message = (uiState as UsersUiState.Error).error
                        ?: stringResource(id = R.string.error_common)
                )
            }

            UsersUiState.Loading -> {
                refreshing = false
                HorizontalProgress()
            }

            is UsersUiState.Success -> {
                refreshing = false
                LazyColumn(
                    modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    (uiState as? UsersUiState.Success)?.users?.let { usersList ->
                        itemsIndexed(usersList) { index, user ->
                            UserListItem(user = user) {
                                deleteUserDialogOpen = true to user.id
                            }
                            Divider(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onAccept: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        text = stringResource(id = R.string.title_delete_dialog),
        positiveButtonText = stringResource(id = R.string.action_positive_delete_dialog),
        negativeButtonText = stringResource(id = R.string.action_negative_delete_dialog),
        onPositiveClick = onAccept
    )
}