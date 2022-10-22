package com.loskon.usercrud.app.userinfo.domain

class UserInfoInteractor(
    private val userInfoRepository: UserInfoRepository
) {

    suspend fun getUserAsFlow(id: Int) = userInfoRepository.getUserAsFlow(id)
}