package com.loskon.usercrud.app.userlist.presentation

import com.loskon.usercrud.app.userlist.domain.UserListInteractor
import com.loskon.usercrud.base.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class UserListViewModel(
    private val userListInteractor: UserListInteractor
) : BaseViewModel() {

    private val userListMutableFlow = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val userListFlow get() = userListMutableFlow.asStateFlow()

    private var job: Job? = null

    fun notifyNoInternet() {
        userListMutableFlow.tryEmit(UserListUiState.NoInternet)
    }

    fun performUsersRequest() {
        job?.cancel()
        job = launchIOJob() {
            userListInteractor.getUsersAsFlow()
                .onStart {
                    userListMutableFlow.emit(UserListUiState.Loading)
                }
                .catch {
                    Timber.d(it)
                    userListMutableFlow.emit(UserListUiState.Failure)
                }
                .collectLatest {
                    userListMutableFlow.emit(UserListUiState.Success(it))
                }
        }
    }
}