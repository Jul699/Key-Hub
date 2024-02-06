package com.citra.keyhub.ui.auth.register

import androidx.lifecycle.ViewModel
import com.citra.keyhub.data.KeyHubRepository

class RegisterViewModel(private val keyHubRepository: KeyHubRepository) : ViewModel() {
    fun register(nomor: String, password: String) = keyHubRepository.register(nomor, password)

    fun addDetailUser(
        userId: String,
        nomor: String,
        nama: String,
        plat: String,
        merek_motor: String,
        kunci: String
    )= keyHubRepository.addDetailUser(userId, nomor, nama, plat, merek_motor, kunci)
}