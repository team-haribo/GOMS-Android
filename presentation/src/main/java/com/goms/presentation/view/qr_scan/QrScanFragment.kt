package com.goms.presentation.view.qr_scan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.presentation.databinding.FragmentQrScanBinding
import com.goms.presentation.view.qr_scan.capture.QrCodeActivity

class QrScanFragment : Fragment() {
    private lateinit var binding: FragmentQrScanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentQrScanBinding.inflate(layoutInflater)
        context?.startActivity(Intent(context, QrCodeActivity::class.java))

        return binding.root
    }
}