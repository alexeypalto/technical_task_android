package com.alexeyp.users.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexeyp.users.UsersRoute

const val USERS_ROUTE = "users"

fun NavGraphBuilder.listUsersScreen(
    onShowSnackbar: (String) -> Unit,
) {
    composable(
        route = USERS_ROUTE,
    ) {
        UsersRoute(onShowSnackbar)
    }
}
