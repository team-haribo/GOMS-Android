package com.goms.presentation.view.qr_scan.capture

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.presentation.databinding.ActivityQrCodeBinding
import com.goms.presentation.view.main.MainActivity

class QrCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrCodeBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            scanningLogic()
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

        codeScanner.decodeCallback = DecodeCallback { text ->
            runOnUiThread {
                Toast.makeText(this, "scan complete: ${text.text}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java)
                    .putExtra("scanAble", true))
                finish()
            }
        }

        codeScanner.errorCallback = ErrorCallback { error ->
            runOnUiThread {
                Toast.makeText(this, "scan error: ${error.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java)
                    .putExtra("scanAble", false))
                finish()
            }
        }

        codeScanner.startPreview()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) scanningLogic()
            else Toast.makeText(this, "권한을 설정해주세요", Toast.LENGTH_SHORT).show()
        }
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

        startActivity(Intent(this, MainActivity::class.java)
            .putExtra("scanAble", false))
        finish()
    }
}