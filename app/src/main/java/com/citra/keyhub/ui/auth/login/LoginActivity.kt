package com.citra.keyhub.ui.auth.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.citra.keyhub.R
import com.citra.keyhub.Result
import com.citra.keyhub.ViewModelFactory
import com.citra.keyhub.data.network.response.login.LoginResponse
import com.citra.keyhub.data.reference.UserPreference
import com.citra.keyhub.databinding.ActivityAuthBinding
import com.citra.keyhub.databinding.ActivityLoginBinding
import com.citra.keyhub.ui.auth.register.RegisterActivity
import com.citra.keyhub.ui.main.MainActivity
import com.citra.keyhub.utils.Util
import java.net.ResponseCache

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private val userPreference by lazy { UserPreference(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val number = binding.edNomor.text.toString()
        val password = binding.edPassword.text.toString()
        if (number.isEmpty()) {
            Toast.makeText(this, "Nomor tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Isi Password Terlebih dahulu", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(number, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            Util.showLoadingDialog(this)
                        }

                        is Result.Error -> {
                            Util.hideLoadingDialog()
                            Log.e(TAG, "login: ${result.error}")
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.ErrorResponse -> {
                            Util.hideLoadingDialog()
                            val responseCode = result.error.toInt()
                            if (responseCode == 400) {
                                Toast.makeText(this, "Gagal login. Cek nomor telepon dan kata sandi anda!", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show()
                            }
                        }

                        is Result.Success -> {
                            Util.hideLoadingDialog()
                            Log.e(TAG, "login: ${result.data}")
                            processLogin(result.data)
                            startActivity(Intent(this, MainActivity::class.java))
                            this.finish()
                        }
                    }
                }
            }
        }
    }

    private fun processLogin(user: LoginResponse) {
        userPreference.idUser = user.data?.id_user.toString()
        userPreference.kunci = user.data?.kunci
        userPreference.name = user.data?.nama
        userPreference.merkMotor = user.data?.merek_motor
        userPreference.plate = user.data?.plat
        userPreference.isLoggedIn = true
    }
}