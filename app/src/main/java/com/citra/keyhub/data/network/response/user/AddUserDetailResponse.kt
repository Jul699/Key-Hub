package com.citra.keyhub.data.network.response.user

data class AddUserDetailResponse(
    val success: Boolean,
    val message : String,
    val data : DataUser
)
