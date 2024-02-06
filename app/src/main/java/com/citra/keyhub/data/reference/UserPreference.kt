package com.citra.keyhub.data.reference

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    companion object{
        private const val PREF_NAME = "UserPreference"
        private const val NUMBER = "number"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var number: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var idUser: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var name: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var plate: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var merkMotor: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var kunci: String?
        get() = preferences.getString(NUMBER, null)
        set(value) = preferences.edit().putString(NUMBER, value).apply()
    var isLoggedIn: Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
}