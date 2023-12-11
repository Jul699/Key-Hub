package com.citra.keyhub.data.network.response.lostkey

data class FoundLostKeyPostResponse (
    val success : Boolean,
    val message : String,
    val data : String
)