package com.goms.presentation.view.qr_scan.capture

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.databinding.FragmentScanCompleteBinding
import com.goms.presentation.view.main.MainActivity

class ScanCompleteFragment : Fragment() {
    private lateinit var binding: FragmentScanCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentScanCompleteBinding.inflate(layoutInflater)

        val sharedPreferences = context?.getSharedPreferences("userOuting", MODE_PRIVATE)
        val outingStatus = sharedPreferences?.getBoolean("outingStatus", false) as Boolean
        binding.qrScanCompleteText.setContent {
            QrScanCompleteText(outingStatus)
        }

        binding.qrScanCompleteFinishButton.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }

        return binding.root
    }

    @Composable
    private fun QrScanCompleteText(outingStatus: Boolean) {
        val textFont = FontFamily(
            Font(R.font.sf_pro_text_medium, FontWeight.Medium),
            Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = textFont,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 35.sp
                    )
                ) {
                    append("QR 스캔 완료!\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = textFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                ) {
                    append(
                        if (outingStatus) "7시 30분까지 늦지 않게 오세요!"
                        else "무사히 복귀하였습니다."
                    )
                }
            },
            textAlign = TextAlign.Center
        )
    }
}