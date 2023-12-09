package com.citra.keyhub.data.network.response.user

data class DataUser(
    val detail_id: Int,
    val id_user: Int,
    val nomor: String,
    val nama: String,
    val plat: String,
    val merek_motor: String,
    val kunci: String
)
