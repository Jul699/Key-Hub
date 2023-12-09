package com.citra.keyhub.data.network.response.login

import com.citra.keyhub.data.network.response.user.DataUser

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: DataUser?
)