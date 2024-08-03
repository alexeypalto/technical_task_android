package com.alexeyp.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyp.common.result.asSuccess
import com.alexeyp.domain.usecase.CreateUserUseCase
import com.alexeyp.domain.usecase.DeleteUserUseCase
import com.alexeyp.domain.usecase.GetUsersByPagesUseCase
import com.alexeyp.domain.usecase.GetUsersNumberUseCase
import com.alexeyp.network.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersNumberUseCase: GetUsersNumberUseCase,
    private val getUsersByPagesUseCase: GetUsersByPagesUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    var shouldDisplayError by mutableStateOf(false to String())

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState>
        get() = _uiState

    private var PAGE = 1
    private val SIZE = 20

    fun refresh() {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            _uiState.value = UsersUiState.Loading
            PAGE = getUsersNumberUseCase.invoke() / SIZE

            runCatching {
                getUsersByPagesUseCase.invoke(PAGE, SIZE)
            }
                .onSuccess { data ->
                    _uiState.value = UsersUiState.Success(users = data.asSuccess().value)
                }
                .onFailure { e ->
                    _uiState.value = UsersUiState.Error(e.message)
                    shouldDisplayError = true to (e.message ?: "Users could not be loaded")
                }
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            runCatching {
                createUserUseCase.invoke(user)
            }
                .onSuccess {
                    val newList = mutableListOf<User>()
                    it.asSuccess().value?.let { newList.add(it.copy(creationDate = System.currentTimeMillis())) }
                    newList.addAll((_uiState.value as? UsersUiState.Success)?.users ?: emptyList())
                    _uiState.value = UsersUiState.Success(users = newList)
                }
                .onFailure {
                    shouldDisplayError = true to (it.message ?: "User could not be added")
                }
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            runCatching {
                deleteUserUseCase.invoke(userId)
            }
                .onSuccess {
                    val newList = mutableListOf<User>()
                    newList.addAll((_uiState.value as? UsersUiState.Success)?.users ?: emptyList())
                    newList.removeAt(newList.indexOfFirst { it.id == userId })
                    _uiState.value = UsersUiState.Success(users = newList)
                }
                .onFailure {
                    shouldDisplayError = true to (it.message ?: "User could not be removed")
                }
        }
    }

    fun clearErrorState() {
        shouldDisplayError = false to String()
    }

}

sealed interface UsersUiState {
    data object Loading : UsersUiState
    data class Success(val users: List<User>) : UsersUiState
    data class Error(val error: String?) : UsersUiState
}
