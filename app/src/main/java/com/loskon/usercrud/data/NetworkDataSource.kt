package com.loskon.usercrud.data

import com.loskon.usercrud.data.api.UserApi
import com.loskon.usercrud.data.dto.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(
    private val userApi: UserApi
) {

    suspend fun getUsersAsFlow(): Flow<List<UserDto>> {
        return flow {
            val response = userApi.getFakeUsers()

            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                emit(emptyList())
            }
        }
    }

    suspend fun getUserAsFlow(id: Int): Flow<UserDto> {
        return flow {
            val response = userApi.getFakeUser()

            if (response.isSuccessful) {
                emit(response.body() ?: UserDto())
            } else {
                emit(UserDto())
            }
        }
    }

    suspend fun addUser(user: UserDto) {
        userApi.addUser(user)
    }

    suspend fun updateUser(id: Int, user: UserDto) {
        userApi.updateUser(id, user)
    }

    suspend fun deleteUser(id: Int) {
        userApi.deleteUser(id)
    }
}