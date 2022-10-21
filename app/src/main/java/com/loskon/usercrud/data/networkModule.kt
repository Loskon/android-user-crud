package com.loskon.usercrud.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loskon.usercrud.BuildConfig
import com.loskon.usercrud.data.api.UserApi
import com.loskon.usercrud.data.converter.LocalDateTimeConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideGsonConverter() }
    single { provideRetrofit(get(), get()) }
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

private fun provideGsonConverter(): Gson {
    return GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter()).create()
}

private fun provideRetrofit(okHttp: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

private fun provideUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
}