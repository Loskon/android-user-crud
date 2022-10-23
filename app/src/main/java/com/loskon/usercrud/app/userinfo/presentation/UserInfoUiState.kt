package com.loskon.usercrud.app.userinfo.presentation

import com.loskon.usercrud.domain.UserModel

sealed class UserInfoUiState {
    object Loading : UserInfoUiState()
    data class Success(val user: UserModel) : UserInfoUiState()
    object AddUser : UserInfoUiState()
    object NoInternet : UserInfoUiState()
    object Error : UserInfoUiState()
}