package com.citra.keyhub.data.network.response.plate

import com.citra.keyhub.data.network.response.user.DataUser

data class SearchPlateResponse (
    val success: Boolean,
    val data: DataUser
)

