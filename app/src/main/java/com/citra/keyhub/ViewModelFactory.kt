package com.citra.keyhub

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.citra.keyhub.data.di.Injection
import com.citra.keyhub.ui.auth.login.LoginViewModel
import com.citra.keyhub.ui.auth.register.RegisterViewModel
import com.citra.keyhub.ui.detection.DetectionViewModel
import com.citra.keyhub.ui.main.MainActiviryViewModel
import org.opencv.dnn.DetectionModel

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java)->{
                LoginViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(DetectionModel::class.java) -> {
                DetectionViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MainActiviryViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository(context)) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}