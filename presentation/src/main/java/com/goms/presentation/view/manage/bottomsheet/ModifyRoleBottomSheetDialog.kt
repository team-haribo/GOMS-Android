package com.goms.presentation.view.manage.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.presentation.R
import com.example.presentation.databinding.BottomSheetModifyRoleBinding
import com.goms.domain.data.council.ModifyRoleRequestData
import com.goms.presentation.viewmodel.CouncilViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class ModifyRoleBottomSheetDialog(private val uuid: UUID): BottomSheetDialogFragment() {
    private val councilViewModel by viewModels<CouncilViewModel>()

    private lateinit var binding: BottomSheetModifyRoleBinding
    private var changeModifyRole = "ROLE_STUDENT"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetModifyRoleBinding.inflate(layoutInflater)
        changeRole()
        binding.modifyFilterModifyRoleButton.setOnClickListener {
            setRole(uuid)
        }

        return binding.root
    }

    private fun setRole(accountIdx: UUID) {
        lifecycleScope.launch {
            if (changeModifyRole == "BLACK_LIST") setBlackListLogic(accountIdx)
            else modifyRoleLogic(accountIdx)
        }
    }

    private suspend fun modifyRoleLogic(accountIdx: UUID) {
        councilViewModel.modifyRole(
            ModifyRoleRequestData(
            accountIdx = accountIdx,
            authority = changeModifyRole
            )
        )

        councilViewModel.modifyRole.collect { checkAble ->
            if (checkAble) {
                Toast.makeText(context, "권한이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
        }
    }

    private suspend fun setBlackListLogic(accountIdx: UUID) {
        councilViewModel.setBlackList(accountIdx)
        councilViewModel.setBlackList.collect { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "외출 금지로 설정되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeRole() {
        val modifyRoleButtons = listOf(binding.modifyFilterAttrStudent, binding.modifyFilterAttrCouncil,
            binding.modifyFilterAttrBlacklist)
        val modifyRoleTexts = listOf("ROLE_STUDENT", "ROLE_STUDENT_COUNCIL", "BLACK_LIST")

        for (button in modifyRoleButtons) {
            button.setOnClickListener {
                for (modifyRole in modifyRoleButtons) {
                    if (modifyRole == button) {
                        modifyRole.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        modifyRole.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                        val currentIndex = modifyRoleButtons.indexOf(modifyRole)
                        changeModifyRole = modifyRoleTexts[currentIndex]
                    } else {
                        modifyRole.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        modifyRole.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }
    }
}