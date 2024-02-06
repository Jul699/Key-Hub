package com.citra.keyhub.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.citra.keyhub.Result
import com.citra.keyhub.data.network.ApiService
import com.citra.keyhub.data.network.response.login.LoginResponse
import com.citra.keyhub.data.network.response.login.PostLogin
import com.citra.keyhub.data.network.response.lostkey.FoundLostKeyPostResponse
import com.citra.keyhub.data.network.response.lostkey.LostKeyGetResponse
import com.citra.keyhub.data.network.response.lostkey.LostKeyPostResponse
import com.citra.keyhub.data.network.response.lostkey.LostKeyUpdateResponse
import com.citra.keyhub.data.network.response.lostkey.PostFoundKey
import com.citra.keyhub.data.network.response.lostkey.PostLostKey
import com.citra.keyhub.data.network.response.plate.SearchPlateResponse
import com.citra.keyhub.data.network.response.register.PostRegister
import com.citra.keyhub.data.network.response.register.RegisterResponse
import com.citra.keyhub.data.network.response.user.AddUserDetailResponse
import com.citra.keyhub.data.network.response.user.PostUserDetail
import com.citra.keyhub.data.network.response.user.UpdateUserDetailResponse
import com.citra.keyhub.data.network.response.user.UpdatedUserDetail

class KeyHubRepository(private val apiService: ApiService) {
    fun register(nomor: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(PostRegister(nomor, password))
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                }
            }  else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }


    fun login(nomor: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(PostLogin(nomor, password))
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Tidak ada data yang diterima dari server"))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }


    fun addDetailUser(
        userId: String,
        nomor: String,
        nama: String,
        plat: String,
        merek_motor: String,
        kunci: String
    ): LiveData<Result<AddUserDetailResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addDetailUser(
                userId,
                PostUserDetail(nomor, nama, plat, merek_motor, kunci)
            )
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Gagal login."))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun updateDetailUser(
        userId: String,
        nomor: String,
        nama: String,
        plat: String,
        merek_motor: String,
        kunci: String
    ): LiveData<Result<UpdateUserDetailResponse>> = liveData {
        try {
            val response = apiService.updateDetailUser(
                userId,
                UpdatedUserDetail(nomor, nama, plat, merek_motor, kunci)
            )
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error("Gagal login."))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun searchPlat(plat: String): LiveData<Result<SearchPlateResponse>> = liveData {
        try {
            val response = apiService.searchUserByPlat(plat)
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun postLostKey(
        userId: String,
        lostDescription: String
    ): LiveData<Result<LostKeyPostResponse>> = liveData {
        try {
            val response = apiService.postLostKey(
                PostLostKey(userId, lostDescription)
            )
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun updateLostKey(
        lostKeyId: String,
        userIdFound: String
    ): LiveData<Result<LostKeyUpdateResponse>> = liveData {
        try {
            val response = apiService.updateLostKey(lostKeyId, userIdFound)
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun getLostKey(): LiveData<Result<LostKeyGetResponse>> = liveData {
        try {
            val response = apiService.getLostKey()
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }

    fun foundLostKey(
        userIdLost: String,
        foundDescription: String,
        userIdFound: String
    ): LiveData<Result<FoundLostKeyPostResponse>> = liveData {
        try {
            val response =
                apiService.postFoundLostKey(PostFoundKey(userIdLost, foundDescription, userIdFound))
            val data = response.body()
            if (response.isSuccessful) {
                if (data != null) {
                    emit(Result.Success(data))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val errorMsg = response.code().toString()
                emit(Result.ErrorResponse(errorMsg))
            }
        } catch (error: Exception) {
            emit(Result.Error("Terjadi kesalahan: ${error.message}"))
        }
    }
}