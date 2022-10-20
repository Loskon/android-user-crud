package com.loskon.usercrud.domain

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserModel(
    val id: Int = 0,
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val birthDate: LocalDateTime = LocalDateTime.now(),
    val email: String = "",
    val phone: String = "",
    val photoUrl: String = ""
)