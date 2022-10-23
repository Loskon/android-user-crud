package com.loskon.usercrud.data.dto

import com.google.gson.annotations.SerializedName
import com.loskon.usercrud.domain.UserModel

data class UserDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("middleName") val middleName: String? = null,
    @SerializedName("birthDate") val birthDate: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null
)

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id = id ?: 0,
        lastName = lastName ?: "",
        firstName = firstName ?: "",
        middleName = middleName ?: "",
        birthDate = birthDate ?: "",
        email = email ?: "",
        phone = phone ?: "",
        photoUrl = photoUrl ?: ""
    )
}

fun UserModel.toUserDto(): UserDto {
    return UserDto(
        id = id,
        lastName = lastName,
        firstName = firstName,
        middleName = middleName,
        birthDate = birthDate,
        email = email,
        phone = phone,
        photoUrl = photoUrl
    )
}