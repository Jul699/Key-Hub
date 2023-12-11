package com.citra.keyhub.data.network.response.lostkey

data class PostFoundKey(
    val userId: String,
    val foundDescription: String,
    val userIdFound: String
)