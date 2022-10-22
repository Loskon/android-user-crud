package com.loskon.usercrud.app.userinfo.presentation

import com.loskon.usercrud.app.userinfo.domain.UserInfoInteractor
import com.loskon.usercrud.base.presentation.BaseViewModel
import com.loskon.usercrud.domain.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class UserInfoViewModel(
    private val userInfoInteractor: UserInfoInteractor
) : BaseViewModel() {

    private val userInfoMutableFlow = MutableStateFlow<UserInfoState>(UserInfoState.Loading)
    private val userMutableFlow = MutableStateFlow(UserModel())
    val userInfoFlow get() = userInfoMutableFlow.asStateFlow()
    val userFlow get() = userMutableFlow.asStateFlow()

    private var job: Job? = null

    fun performAddUser() {
        userInfoMutableFlow.tryEmit(UserInfoState.AddUser)
    }

    fun performUserRequest(id: Int) {
        job?.cancel()
        job = launchIOJob() {
            userInfoInteractor.getUserAsFlow(id)
                .onStart {
                    userInfoMutableFlow.emit(UserInfoState.Loading)
                }
                .catch {
                    Timber.e(it)
                    userInfoMutableFlow.emit(UserInfoState.Error)
                }
                .collectLatest {
                    userMutableFlow.emit(it)
                    userInfoMutableFlow.emit(UserInfoState.Success(it))
                }
        }
    }

    fun addUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    fun updateUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    fun deleteUser(user: UserModel) {
        TODO("Not yet implemented")
    }
}