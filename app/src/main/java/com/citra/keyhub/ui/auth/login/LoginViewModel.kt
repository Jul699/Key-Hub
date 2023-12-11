package com.citra.keyhub.ui.auth.login

import androidx.lifecycle.ViewModel
import com.citra.keyhub.data.KeyHubRepository

class LoginViewModel(private val keyHubRepository: KeyHubRepository): ViewModel() {
    fun login(nomor:String,password: String)=keyHubRepository.login(nomor,password)
}