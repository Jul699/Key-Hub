package com.citra.keyhub.utils

import android.content.Context
import com.citra.keyhub.R
import java.io.File
import java.io.FileOutputStream


object OpenCv {
    fun loadCascadeFile(context:Context): File {
        val cascadeFile = File(context.cacheDir, "plate_cascade.xml")
        if (!cascadeFile.exists()) {
            val inputStream = context.resources.openRawResource(R.raw.haarcascade_russian_plate_number)
            val outputStream = FileOutputStream(cascadeFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }
        return cascadeFile
    }
}