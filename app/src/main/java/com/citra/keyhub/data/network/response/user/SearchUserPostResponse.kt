package com.citra.keyhub.data.network.response.user

import android.provider.ContactsContract.Data

data class SearchUserPostResponse(
    val success: Boolean,
    val data:DataUser
)
