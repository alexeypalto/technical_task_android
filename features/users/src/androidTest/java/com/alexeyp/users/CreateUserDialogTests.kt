package com.alexeyp.users

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexeyp.network.model.User
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateUserDialogTests {

    //this file could not run yet cuz of error "6 files found with path 'META-INF/LICENSE.md'."

    @get:Rule
    val composeTestRule = createComposeRule()

    private val user = User(
        id = 7270216L,
        name = "Vrund Pilla",
        email = "pilla_vrund@marks.example",
        gender = "male",
        status = "active"
    )

    @Test
    fun testCreateUserDialogRenders() {
        composeTestRule.setContent {
            CreateUserDialog(
                onUserCreated = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Create User").assertExists()
        composeTestRule.onNodeWithText("Name").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Status").assertExists()
        composeTestRule.onNodeWithText("Gender").assertExists()
    }

    @Test
    fun testCreateUserDialogInput() {
        composeTestRule.setContent {
            CreateUserDialog(
                onUserCreated = {},
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Name").performTextInput(user.name)
        composeTestRule.onNodeWithText("Email").performTextInput(user.email)

        composeTestRule.onNodeWithText(user.name).assertExists()
        composeTestRule.onNodeWithText(user.email).assertExists()
    }

    @Test
    fun testCreateUserDialogOnUserCreated() {
        var userCreated: User? = null

        composeTestRule.setContent {
            CreateUserDialog(
                onUserCreated = {
                    userCreated = it
                },
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Name").performTextInput(user.name)
        composeTestRule.onNodeWithText("Email").performTextInput(user.email)
        composeTestRule.onNodeWithText("Create").performClick()

        assert(userCreated != null)
        assert(userCreated?.name == user.name)
        assert(userCreated?.email == user.email)
        assert(userCreated?.gender == user.gender)
        assert(userCreated?.status == user.status)
    }
}