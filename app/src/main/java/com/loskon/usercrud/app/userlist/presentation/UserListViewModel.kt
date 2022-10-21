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
): BaseViewModel() {

    private val userListMutableStateFlow = MutableStateFlow<UserListState>(UserListState.Loading)
    val userListStateFlow get() = userListMutableStateFlow.asStateFlow()

    private var job: Job? = null

    fun performUsersRequest() {
        job?.cancel()
        job = launchIOJob() {
            userListInteractor.getUsersAsFlow()
                .onStart {
                    userListMutableStateFlow.emit(UserListState.Loading)
                }
                .catch {
                    Timber.d(it)
                    userListMutableStateFlow.emit(UserListState.Failure)
                }
                .collectLatest {
                    userListMutableStateFlow.emit(UserListState.Success(it))
                }
        }
    }
}