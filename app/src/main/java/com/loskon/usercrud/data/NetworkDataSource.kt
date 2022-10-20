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
            val response = userApi.getUser(id)

            if (response.isSuccessful) {
                emit(response.body() ?: UserDto())
            } else {
                emit(UserDto())
            }
        }
    }
}