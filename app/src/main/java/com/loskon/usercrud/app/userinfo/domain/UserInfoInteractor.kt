package com.loskon.usercrud.app.userinfo.domain

import com.loskon.usercrud.domain.UserModel

class UserInfoInteractor(
    private val userInfoRepository: UserInfoRepository
) {

    suspend fun getUserAsFlow(id: Int) = userInfoRepository.getUserAsFlow(id)

    suspend fun addUser(user: UserModel) = userInfoRepository.addUser(user)

    suspend fun updateUser(id: Int, user: UserModel) = userInfoRepository.updateUser(id, user)

    suspend fun deleteUser(id: Int) = userInfoRepository.deleteUser(id)
}