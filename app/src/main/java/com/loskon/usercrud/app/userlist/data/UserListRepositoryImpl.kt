package com.loskon.usercrud.app.userlist.data

import com.loskon.usercrud.app.userlist.domain.UserListRepository
import com.loskon.usercrud.data.NetworkDataSource
import com.loskon.usercrud.data.dto.toUserModel
import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserListRepository {

    override suspend fun getUsersAsFlow(): Flow<List<UserModel>> {
        return networkDataSource.getUsersAsFlow().map { it.map { userDto -> userDto.toUserModel() } }
    }
}