package com.loskon.usercrud.app.userinfo.domain

import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    suspend fun getUserAsFlow(id: Int): Flow<UserModel>

    suspend fun addUser(user: UserModel)

    suspend fun updateUser(id: Int, user: UserModel)

    suspend fun deleteUser(id: Int)
}