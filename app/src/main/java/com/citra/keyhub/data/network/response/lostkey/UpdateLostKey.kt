package com.citra.keyhub.data.network.response.lostkey

data class UpdateLostKey (
    val status : Boolean,
    val userIdFound: String
)