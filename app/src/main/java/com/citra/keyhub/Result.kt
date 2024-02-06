package com.citra.keyhub

import okhttp3.ResponseBody
import java.lang.Error

sealed class Result<out R> private constructor()  {
    data class Success<out T>(val data: T) : Result<T>()
    data class ErrorResponse<out T>(val error: String) : Result<T>()
    data class Error<out T>(val error: String): Result<T>()
    object Loading : Result<Nothing>()
}