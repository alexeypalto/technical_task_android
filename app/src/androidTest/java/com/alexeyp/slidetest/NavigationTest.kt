package com.alexeyp.slidetest

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

@HiltAndroidTest
class NavigationTest {
    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 3)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(resId) }

    // The strings used for matching in these tests
    private val usersTitle by composeTestRule.stringResource(com.alexeyp.users.R.string.title_users)

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun firstScreen_isForYou() {
        composeTestRule.apply {
            // VERIFY for you is selected
            onNodeWithText(usersTitle).assertIsSelected()
        }
    }
}