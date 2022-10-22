package com.loskon.usercrud.app.userinfo

import com.loskon.usercrud.app.userinfo.data.UserInfoRepositoryImpl
import com.loskon.usercrud.app.userinfo.domain.UserInfoInteractor
import com.loskon.usercrud.app.userinfo.domain.UserInfoRepository
import com.loskon.usercrud.app.userinfo.presentation.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userInfoModule = module {
    single<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
    factory { UserInfoInteractor(get()) }
    viewModel { UserInfoViewModel(get()) }
}