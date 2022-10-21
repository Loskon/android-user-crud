package com.loskon.usercrud.app.userlist.domain

class UserListInteractor(
    private val userListRepository: UserListRepository
) {
    suspend fun getUsersAsFlow() = userListRepository.getUsersAsFlow()
}