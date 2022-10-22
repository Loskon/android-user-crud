package com.loskon.usercrud.app.userinfo.domain

import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    suspend fun getUserAsFlow(id: Int): Flow<UserModel>
}