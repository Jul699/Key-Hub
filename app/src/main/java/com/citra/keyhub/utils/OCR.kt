package com.citra.keyhub.utils

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Rect

object OCR {
    fun isValidLicensePlate(licensePlate: String): Boolean {
        val cleanedLicensePlate = licensePlate.replace("[^A-Za-z0-9]".toRegex(), "")
        val regex = "^[A-Z]{1,2}\\d{1,4}[A-Z]{1,3}$".toRegex()
        return regex.matches(cleanedLicensePlate)
    }
    fun recognizeText(imageText: Bitmap, onResult: (String) -> Unit, onError: () -> Unit) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(imageText, 0)
        recognizer.process(image)
            .addOnSuccessListener { result ->
                val recognizedText = result.text
                onResult(recognizedText)
            }
            .addOnFailureListener {
                onError()
            }
    }
    fun recognizeTextOnPlate(mat: Mat, plateRect: Rect, onResult: (String) -> Unit, onError: () -> Unit) {
        val roiMat = Mat(mat, plateRect)
        val roiBitmap = Bitmap.createBitmap(roiMat.cols(), roiMat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(roiMat, roiBitmap)
        OCR.recognizeText(roiBitmap,
            onResult = { recognizedText ->
                onResult(recognizedText)
            },
            onError = {
                onError()
            }
        )
    }
}