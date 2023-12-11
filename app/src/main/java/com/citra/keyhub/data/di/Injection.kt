package com.citra.keyhub.data.di

import android.content.Context
import com.citra.keyhub.data.KeyHubRepository
import com.citra.keyhub.data.network.ApiConfig

object Injection{
    fun provideRepository(context:Context):KeyHubRepository{
        val apiService = ApiConfig.getApiService(context)
        return KeyHubRepository(apiService)
    }
}