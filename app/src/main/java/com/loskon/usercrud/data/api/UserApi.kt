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

    @GET("4x_ohr") fun getFakeUsers(): Response<List<UserDto>>

    @GET("users") fun getUsers(): Response<List<UserDto>>

    @GET("users/{id}") fun getUser(@Path("id") id: Int): Response<UserDto>

    @POST("add") fun addUser(@Body user: UserDto?): Response<UserDto>

    @PUT("update/{id}") fun updateUser(@Path("id") id: Int, @Body user: UserDto): Response<UserDto>

    @DELETE("delete/{id}") fun deleteUser(@Path("id") id: Int): Response<UserDto>
}