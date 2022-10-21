package com.loskon.usercrud.data.api

import com.loskon.usercrud.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("JWvp7c") suspend fun getFakeUsers(): Response<List<UserDto>>

    @GET("users") suspend fun getUsers(): Response<List<UserDto>>

    @GET("users/{id}") suspend fun getUser(@Path("id") id: Int): Response<UserDto>

    @POST("add") suspend fun addUser(@Body user: UserDto?): Response<UserDto>

    @PUT("update/{id}") suspend fun updateUser(@Path("id") id: Int, @Body user: UserDto): Response<UserDto>

    @DELETE("delete/{id}") suspend fun deleteUser(@Path("id") id: Int): Response<UserDto>
}