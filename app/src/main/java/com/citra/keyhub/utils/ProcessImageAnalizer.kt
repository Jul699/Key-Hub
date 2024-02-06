package com.citra.keyhub.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier

class ProcessImageAnalyzer(
    private val context: Context,
    val runOnUiThread: (Bitmap) -> Unit,
    private val listener: OnLicensePlateDetectedListener
) : ImageAnalysis.Analyzer {
    private var plateDetected = false
    private val plateCascade: CascadeClassifier by lazy {
        val cascadeFile = OpenCv.loadCascadeFile(context)
        CascadeClassifier(cascadeFile.absolutePath)
    }
    override fun analyze(image: ImageProxy) {
        if (!plateDetected) {
            val mat: Mat = CameraUtil.getMatFromImage(image)
            val plateRectangles = MatOfRect()
            plateCascade.detectMultiScale(mat, plateRectangles, 1.1, 4)
            if (plateRectangles.toArray().isNotEmpty()) {
                val firstPlate = plateRectangles.toArray()[0]
                Imgproc.rectangle(mat, firstPlate.tl(), firstPlate.br(), Scalar(255.0, 0.0, 0.0), 1)
                OCR.recognizeTextOnPlate(mat, firstPlate, onResult = { recognizedText ->
                    if (OCR.isValidLicensePlate(recognizedText)) {
                        val cleanPlate = recognizedText.replace("[^A-Za-z0-9]".toRegex(), "")
                        listener.onLicensePlateDetected(cleanPlate)
                        plateDetected = true
                    } else {
                        listener.onNoLicensePlateDetected()
                    }
                }, onError = {
                    Log.e("TextRecognition", "Error: No text detected on plate")
                })

            }
            val bitmap: Bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(mat, bitmap)
            runOnUiThread(bitmap)
            image.close()
        } else {
            image.close()
        }
    }

}

interface OnLicensePlateDetectedListener {
    fun onLicensePlateDetected(plateNumber : String)
    fun onNoLicensePlateDetected()
}
