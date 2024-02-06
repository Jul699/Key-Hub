package com.citra.keyhub.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import com.citra.keyhub.R

object Util {
    private var alertDialog: AlertDialog? = null

    fun showLoadingDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        builder.setView(inflater.inflate(R.layout.dialog_loading, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        alertDialog?.show()
    }

    fun hideLoadingDialog() {
        alertDialog?.dismiss()
    }
}