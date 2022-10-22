package com.loskon.usercrud.domain

data class UserModel(
    val id: Int = 0,
    var lastName: String = "",
    var firstName: String = "",
    var middleName: String = "",
    var birthDate: String = "",
    var email: String = "",
    var phone: String = "",
    var photoUrl: String = ""
) {
    val fullName: String get() = "$lastName $firstName $middleName"
}