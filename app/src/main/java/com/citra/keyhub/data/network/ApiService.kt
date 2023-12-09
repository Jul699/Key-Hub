package com.citra.keyhub.data.network

import com.citra.keyhub.data.network.response.login.LoginResponse
import com.citra.keyhub.data.network.response.login.PostLogin
import com.citra.keyhub.data.network.response.lostkey.LostKeyGetResponse
import com.citra.keyhub.data.network.response.lostkey.LostKeyPostResponse
import com.citra.keyhub.data.network.response.lostkey.LostKeyUpdateResponse
import com.citra.keyhub.data.network.response.lostkey.PostLostKey
import com.citra.keyhub.data.network.response.lostkey.UpdateLostKey
import com.citra.keyhub.data.network.response.plate.SearchPlateResponse
import com.citra.keyhub.data.network.response.register.PostRegister
import com.citra.keyhub.data.network.response.register.RegisterResponse
import com.citra.keyhub.data.network.response.user.AddUserDetailResponse
import com.citra.keyhub.data.network.response.user.PostUserDetail
import com.citra.keyhub.data.network.response.user.UpdateUserDetailResponse
import com.citra.keyhub.data.network.response.user.UpdatedUserDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("users/signup")
    suspend fun register(@Body register:PostRegister): Response<RegisterResponse>

    @POST("users/login")
    suspend fun login(@Body user:PostLogin): Response<LoginResponse>

    @POST("User/{userId}/add")
    suspend fun addDetailUser(@Path("userId")userId: String,@Body userDetail:PostUserDetail): Response<AddUserDetailResponse>

    @PUT("users/{userId}/update")
    suspend fun updateDetailUser(@Path("userId") userId: String, @Body updatedUserDetail: UpdatedUserDetail): Response<UpdateUserDetailResponse>

    @GET("users/search/{plat}")
    suspend fun searchUserByPlat(@Path("plat") plat: String): Response<SearchPlateResponse>

    @POST("lostkey")
    suspend fun postLostKey(@Body postLostKey: PostLostKey): Response<LostKeyPostResponse>

    @GET("lostkey")
    suspend fun getLostKey(): Response<LostKeyGetResponse>

    @PUT("lostkey/{lostKeyId}/update")
    suspend fun updateLostKey(@Path("lostKeyId") lostKeyId: String, @Body updateLostKey: UpdateLostKey): Response<LostKeyUpdateResponse>
}