package com.loskon.usercrud.app.userlist.domain

import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    suspend fun getUsersAsFlow(): Flow<List<UserModel>>
}