package com.loskon.usercrud.app.userinfo.data

import com.loskon.usercrud.app.userinfo.domain.UserInfoRepository
import com.loskon.usercrud.data.NetworkDataSource
import com.loskon.usercrud.data.dto.toUserModel
import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoRepositoryImpl(
    private val networkDataSource: NetworkDataSource
): UserInfoRepository {

    override suspend fun getUserAsFlow(id: Int): Flow<UserModel> {
        return networkDataSource.getUserAsFlow(id).map { it.toUserModel() }
    }
}