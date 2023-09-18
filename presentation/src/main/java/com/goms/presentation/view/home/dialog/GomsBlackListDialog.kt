package com.goms.presentation.view.home.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.goms.presentation.databinding.GomsBlackListDialogBinding
import com.goms.presentation.view.main.MainActivity

class GomsBlackListDialog: DialogFragment() {
    private lateinit var binding: GomsBlackListDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GomsBlackListDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.blackListDialogButtonYes.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            dismiss()
        }

        return binding.root
    }
}