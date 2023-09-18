package com.goms.presentation.utils.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.goms.presentation.databinding.GomsDialogBinding
import com.goms.presentation.view.manage.StudentManageActivity
import com.goms.presentation.view.sign_in.SignInActivity
import com.goms.presentation.viewmodel.CouncilViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class GomsDialog(
    private val title: String,
    private val content: String,
    private val accountIdx: UUID? = null
): DialogFragment() {
    private val councilViewModel by viewModels<CouncilViewModel>()
    private lateinit var binding: GomsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GomsDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogTitleText.text = title
        binding.dialogContentText.text = content

        binding.dialogButtonYes.setOnClickListener {
            lifecycleScope.launch {
                if (tag == "logout") {
                    logoutLogic()
                } else if (tag == "setBlackList") {
                    if (accountIdx != null) setBlackListLogic(accountIdx)
                } else if (tag == "cancelBlackList") {
                    if (accountIdx != null) cancelBlackListLogic(accountIdx)
                }
            }
        }

        binding.dialogButtonNo.setOnClickListener {
            dialog?.dismiss()
        }

        return binding.root
    }

    private fun logoutLogic() {
        val tokenInfo = context?.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        tokenInfo?.edit()?.clear()?.apply()

        startActivity(Intent(context, SignInActivity::class.java))
        dismiss()
    }

    private suspend fun setBlackListLogic(accountIdx: UUID) {
        councilViewModel.setBlackList(
            accountIdx = accountIdx,
            activity = activity as StudentManageActivity
        )
        councilViewModel.setBlackList.collect { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "외출 금지로 설정되었습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private suspend fun cancelBlackListLogic(accountIdx: UUID) {
        councilViewModel.cancelBlackList(
            accountIdx = accountIdx,
            activity = activity as StudentManageActivity
        )
        councilViewModel.cancelBlackList.collect { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "외출 금지를 해제했습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}