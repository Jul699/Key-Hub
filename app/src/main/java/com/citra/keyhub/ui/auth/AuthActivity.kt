package com.citra.keyhub.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.citra.keyhub.R
import com.citra.keyhub.data.reference.UserPreference
import com.citra.keyhub.ui.auth.login.LoginActivity
import com.citra.keyhub.ui.main.MainActivity

class AuthActivity : AppCompatActivity() {

    private val userPreference by lazy { UserPreference(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Log.e(TAG, "tes login: ${userPreference.isLoggedIn}", )
        if (userPreference.isLoggedIn){
            navigate(Intent (this, MainActivity::class.java))
        }else{
            navigate(Intent (this, LoginActivity::class.java))
        }
    }
    private fun navigate(intent: Intent) {
        val SPLASH_SCREEN_TIMER: Long = 2000
        val splashTimer: Long = SPLASH_SCREEN_TIMER
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, splashTimer)
    }
}