package com.loskon.usercrud.data

import com.loskon.usercrud.BuildConfig
import com.loskon.usercrud.data.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideRetrofit(get()) }
    single { provideUserApi(get()) }

    single { NetworkDataSource(get()) }
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

private fun provideOkHttp(logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(logging)
    }.build()
}

private fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
}