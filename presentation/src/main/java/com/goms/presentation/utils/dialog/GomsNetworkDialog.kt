package com.goms.presentation.utils.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.goms.presentation.databinding.GomsNetworkDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GomsNetworkDialog(private val retryLogic: suspend () -> Unit): DialogFragment() {
    private lateinit var binding: GomsNetworkDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = GomsNetworkDialogBinding.inflate(layoutInflater)

        dialog?.setCanceledOnTouchOutside(false)

        binding.dialogRetryButton.setOnClickListener {
            lifecycleScope.launch {
                retryLogic()
                dismiss()
            }
        }

        return binding.root
    }
}