package com.citra.keyhub.data.network.response.lostkey

data class LostKeyGetResponse(
    val id_lost_key: Int,
    val id_user_lost: Int,
    val lost_description : String,
    val time: String,
    val status: Boolean

)
