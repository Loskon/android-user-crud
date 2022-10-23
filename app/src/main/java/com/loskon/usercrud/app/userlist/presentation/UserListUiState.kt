package com.loskon.usercrud.app.userlist.presentation

import com.loskon.usercrud.domain.UserModel

sealed class UserListUiState {
    object Loading : UserListUiState()
    data class Success(val users: List<UserModel>) : UserListUiState()
    object NoInternet : UserListUiState()
    object Failure : UserListUiState()
}