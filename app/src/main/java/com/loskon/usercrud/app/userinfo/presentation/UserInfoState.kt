package com.loskon.usercrud.app.userinfo.presentation

import com.loskon.usercrud.domain.UserModel

sealed class UserInfoState {
    object Loading : UserInfoState()
    data class Success(val user: UserModel) : UserInfoState()
    object AddUser : UserInfoState()
    object Error : UserInfoState()
}