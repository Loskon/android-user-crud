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

    private val userInfoMutableFlow = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Loading)
    private val userMutableFlow = MutableStateFlow(UserModel())
    val userInfoFlow get() = userInfoMutableFlow.asStateFlow()
    val userFlow get() = userMutableFlow.asStateFlow()

    private var job: Job? = null

    fun prepareAddUser() {
        userInfoMutableFlow.tryEmit(UserInfoUiState.AddUser)
    }

    fun notifyNoInternet() {
        userInfoMutableFlow.tryEmit(UserInfoUiState.NoInternet)
    }

    fun performUserRequest(id: Int) {
        job?.cancel()
        job = launchIOJob {
            userInfoInteractor.getUserAsFlow(id)
                .onStart {
                    userInfoMutableFlow.emit(UserInfoUiState.Loading)
                }
                .catch {
                    Timber.e(it)
                    userInfoMutableFlow.emit(UserInfoUiState.Error)
                }
                .collectLatest {
                    userMutableFlow.emit(it)
                    userInfoMutableFlow.emit(UserInfoUiState.Success(it))
                }
        }
    }

    fun addUser(user: UserModel) {
        launchIOJob {
            userInfoInteractor.addUser(user)
        }
    }

    fun updateUser(id: Int, user: UserModel) {
        launchIOJob {
            userInfoInteractor.updateUser(id, user)
        }
    }

    fun deleteUser(id: Int) {
        launchIOJob {
            userInfoInteractor.deleteUser(id)
        }
    }
}