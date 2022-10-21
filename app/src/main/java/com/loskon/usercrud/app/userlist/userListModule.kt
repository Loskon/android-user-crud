package com.loskon.usercrud.app.userlist

import com.loskon.usercrud.app.userlist.data.UserListRepositoryImpl
import com.loskon.usercrud.app.userlist.domain.UserListInteractor
import com.loskon.usercrud.app.userlist.domain.UserListRepository
import com.loskon.usercrud.app.userlist.presentation.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userListModule = module {
    single<UserListRepository> { UserListRepositoryImpl(get()) }
    factory { UserListInteractor(get()) }
    viewModel { UserListViewModel(get()) }
}