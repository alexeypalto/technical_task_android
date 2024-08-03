package com.alexeyp.slidetest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.alexeyp.slidetest.ui.AppState
import com.alexeyp.users.navigation.USERS_ROUTE
import com.alexeyp.users.navigation.listUsersScreen

@Composable
fun NavHost(
    appState: AppState,
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = USERS_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        listUsersScreen(
            onShowSnackbar = onShowSnackbar,
        )
    }
}
