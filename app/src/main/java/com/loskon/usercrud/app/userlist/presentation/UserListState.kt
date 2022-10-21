package com.loskon.usercrud.app.userlist.presentation

import com.loskon.usercrud.domain.UserModel

sealed class UserListState {
    object Loading : UserListState()
    data class Success(val users: List<UserModel>) : UserListState()
    object Failure : UserListState()
}