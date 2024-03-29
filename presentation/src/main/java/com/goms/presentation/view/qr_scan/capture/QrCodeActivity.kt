package com.goms.presentation.view.qr_scan.capture

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.goms.presentation.databinding.ActivityQrCodeBinding
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.viewmodel.OutingViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class QrCodeActivity : AppCompatActivity() {
    private val outingViewModel by viewModels<OutingViewModel>()

    private lateinit var binding: ActivityQrCodeBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPermission()
        binding.qrScanQuitScanner.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun scanningLogic() {
        codeScanner = CodeScanner(this, binding.codeScannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }

        codeScanner.decodeCallback = DecodeCallback { resultUrl ->
            runOnUiThread {
                lifecycleScope.launch {
                    outingLogic(resultUrl.text)
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback { error ->
            runOnUiThread {
                Toast.makeText(this, "scan error: ${error.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        codeScanner.startPreview()
    }

    private suspend fun outingLogic(text: String) {
        val qrUUID = text.split("/")
        val resultUUID = UUID.fromString(qrUUID[qrUUID.lastIndex])

        outingViewModel.outingLogic(
            outingUUID = resultUUID,
            activity = this
        )
        outingViewModel.isOuting.collect { outAble ->
            if (outAble == true) {
                MainActivity.getInstance().navigateComplete()
                finish()
            }
        }
    }

    private fun setPermission() {
        TedPermission.create()
            .setPermissionListener(object :PermissionListener {
                override fun onPermissionGranted() {
                    scanningLogic()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    startActivity(Intent(this@QrCodeActivity, MainActivity::class.java))
                }
            })
            .setDeniedMessage("카메라 권한이 거부되었습니다.\n권한을 설정하려면\n\n[앱 정보] > [권한]에서 설정하세요.")
            .setPermissions(Manifest.permission.CAMERA)
            .check()
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized)
            codeScanner.startPreview()
    }

    override fun onPause() {
        if (::codeScanner.isInitialized)
            codeScanner.releaseResources()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}