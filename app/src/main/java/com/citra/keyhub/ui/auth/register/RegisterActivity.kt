package com.citra.keyhub.ui.auth.register

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
import com.citra.keyhub.databinding.ActivityRegisterBinding
import com.citra.keyhub.ui.auth.login.LoginActivity
import com.citra.keyhub.utils.Util

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[RegisterViewModel::class.java]
        binding.btDaftar.setOnClickListener {
            register()
        }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register() {
        val number = binding.edNomorRegister.text.toString()
        val password = binding.edPasswordRegister.text.toString()
        val retypePw = binding.edRetypePasswordRegister.text.toString()
        if(number.isNullOrEmpty()){
            Toast.makeText(this, "Silahkan Isi nomor telepon", Toast.LENGTH_SHORT).show()
        }else if (password.isNullOrEmpty()){
            Toast.makeText(this, "Isi password terlebih dahulu", Toast.LENGTH_SHORT).show()
        }else if (retypePw.isNullOrEmpty()){
            Toast.makeText(this, "Isi password lagi!", Toast.LENGTH_SHORT).show()
        }else if (password != retypePw){
            Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show()
        }else{
            val truePassword = retypePw
            viewModel.register(number,truePassword).observe(this){result->
                if(result != null){
                    when (result){
                        is Result.Loading -> {
                            Util.showLoadingDialog(this)
                        }
                        is Result.Error -> {
                            Util.hideLoadingDialog()
                            Log.e(TAG, "register: ${result.error}", )
                        }
                        is Result.ErrorResponse -> {
                            Util.hideLoadingDialog()

                        }
                        is Result.Success -> {
                            Util.hideLoadingDialog()
                            startActivity(Intent(this,LoginActivity::class.java))
                            this.finish()
                        }
                    }
                }
            }
        }
    }
}