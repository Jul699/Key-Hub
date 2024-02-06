package com.citra.keyhub.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.nio.ByteBuffer
import java.util.concurrent.Executors


object CameraUtil {
    fun startCamera(
        context: Context,
        imageAnalyzer: ProcessImageAnalyzer
    ) {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                try {
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val imageAnalysis = ImageAnalysis.Builder().build()
                    imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), imageAnalyzer)
                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        (context as LifecycleOwner),
                        cameraSelector,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.e("error", "[startCamera] Use case binding failed", e)
                }
            },
            ContextCompat.getMainExecutor(context)
        )
    }


    fun getMatFromImage(image: ImageProxy): Mat {
        val yBuffer: ByteBuffer = image.planes[0].buffer
        val uBuffer: ByteBuffer = image.planes[1].buffer
        val vBuffer: ByteBuffer = image.planes[2].buffer
        val ySize: Int = yBuffer.remaining()
        val uSize: Int = uBuffer.remaining()
        val vSize: Int = vBuffer.remaining()
        val nv21 = ByteArray(ySize + uSize + vSize)
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)
        val yuv = Mat(image.height + image.height / 2, image.width, CvType.CV_8UC1)
        yuv.put(0, 0, nv21)
        var mat = Mat()
        Imgproc.cvtColor(yuv, mat, Imgproc.COLOR_YUV2RGB_NV21, 3)
        mat = fixMatRotation(mat, image.imageInfo.rotationDegrees)
        return mat
    }

    private fun fixMatRotation(matOrg: Mat, rotationDegrees: Int): Mat {
        val mat: Mat
        when (rotationDegrees) {
            0 -> {
                mat = matOrg
            }
            90 -> {
                mat = Mat(matOrg.cols(), matOrg.rows(), matOrg.type())
                Core.transpose(matOrg, mat)
                Core.flip(mat, mat, 1)
            }
            180 -> {
                mat = matOrg
                Core.flip(mat, mat, -1)
            }
            else -> {
                mat = matOrg
            }
        }
        return mat
    }

}