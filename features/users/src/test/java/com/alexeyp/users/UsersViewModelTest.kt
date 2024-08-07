package com.alexeyp.users

import com.alexeyp.common.result.Result
import com.alexeyp.domain.usecase.CreateUserUseCase
import com.alexeyp.domain.usecase.DeleteUserUseCase
import com.alexeyp.domain.usecase.GetUsersByPagesUseCase
import com.alexeyp.domain.usecase.GetUsersNumberUseCase
import com.alexeyp.network.model.User
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.Timeout


@ExperimentalCoroutinesApi
class UsersViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val globalTimeout: TestRule = Timeout.seconds(10) // global timeout to avoid infinite loops

    private val getUsersNumberUseCase = mockk<GetUsersNumberUseCase>()
    private val getUsersByPagesUseCase = mockk<GetUsersByPagesUseCase>()
    private val createUserUseCase = mockk<CreateUserUseCase>()
    private val deleteUserUseCase = mockk<DeleteUserUseCase>()
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UsersViewModel(
            getUsersNumberUseCase,
            getUsersByPagesUseCase,
            createUserUseCase,
            deleteUserUseCase
        )
    }

    private val user = User(
        id = 7270216L,
        name = "Vrund Pilla",
        email = "pilla_vrund@marks.example",
        gender = "male",
        status = "active",
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `test getUsers success`() = runTest {
        coEvery { getUsersNumberUseCase.invoke() } returns 100
        coEvery { getUsersByPagesUseCase.invoke(any(), any()) } returns Result.Success.Value(
            listOf(
                user
            )
        )

        viewModel.getUsers()

        assertEquals(
            UsersUiState.Success(listOf(user)),
            viewModel.uiState.value
        )
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `test getUsers failure`() = runTest {
//        coEvery { getUsersNumberUseCase.invoke() } returns 100
//        coEvery { getUsersByPagesUseCase.invoke(any(), any()) } returns Result.Failure.Error(Throwable("Error"))
//
//        viewModel.getUsers()
//
//        assertTrue(viewModel.shouldDisplayError.first)
//    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test createUser success`() = runTest {
        coEvery { createUserUseCase.invoke(user) } returns Result.Success.Value(user)

        viewModel.createUser(user)

        assertTrue(viewModel.uiState.value is UsersUiState.Success)
        assertEquals(1, (viewModel.uiState.value as UsersUiState.Success).users.size)
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `test createUser failure`() = runTest {
//        coEvery { createUserUseCase.invoke(user) } returns Result.Failure.Error(Throwable("Error"))
//
//        viewModel.createUser(user)
//
//        assertTrue(viewModel.shouldDisplayError.first)
//    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test deleteUser success`() = runTest {
        val userList = listOf(user)
        viewModel._uiState.value = UsersUiState.Success(userList)
        coEvery { deleteUserUseCase.invoke(user.id ?: -1L) } returns Result.Success.Value((Unit))

        viewModel.deleteUser(user.id ?: -1L)

        assertTrue(viewModel.uiState.value is UsersUiState.Success)
        assertTrue((viewModel.uiState.value as UsersUiState.Success).users.isEmpty())
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `test deleteUser failure`() = runTest {
//        coEvery { deleteUserUseCase.invoke(user.id ?: -1L) } returns Result.Failure.Error(Throwable("Error"))
//
//        viewModel.deleteUser(user.id ?: -1L)
//
//        assertTrue(viewModel.shouldDisplayError.first)
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        clearAllMocks()
    }
}