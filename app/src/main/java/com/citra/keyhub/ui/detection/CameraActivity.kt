package com.citra.keyhub.ui.detection

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.citra.keyhub.ui.auth.login.LoginActivity
import com.citra.keyhub.databinding.ActivityCameraBinding
import com.citra.keyhub.utils.CameraUtil
import com.citra.keyhub.utils.OnLicensePlateDetectedListener
import com.citra.keyhub.utils.ProcessImageAnalyzer
import org.opencv.android.OpenCVLoader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity(), OnLicensePlateDetectedListener {

    private lateinit var viewBinding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService

    init {
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "openCv: Loaded")
        } else {
            Log.e(TAG, "openCv: Failed to load")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        CameraUtil.startCamera(
            this,
            ProcessImageAnalyzer(
                this,
                { runOnUiThread { viewBinding.viewFinder.setImageBitmap(it) } },
                this
            )
        )
    }
    override fun onLicensePlateDetected(licensePlate: String) {
        cameraExecutor.shutdown()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onNoLicensePlateDetected() {
        Log.e(TAG, "onNoLicensePlateDetected: no plate detected" )
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}


