package com.goms.presentation.view.manage.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.presentation.R
import com.example.presentation.databinding.BottomSheetFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetFilterBinding.inflate(layoutInflater)

        bottomSheetLogic()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun bottomSheetLogic() {
        val roleButtons = listOf(binding.filterAttrStudent, binding.filterAttrCouncil, binding.filterAttrBlacklist)
        val roleTexts = listOf("ROLE_STUDENT", "ROLE_STUDENT_COUNCIL", "BLACK_LIST")

        for (button in roleButtons) {
            button.setOnClickListener {
                for (role in roleButtons) {
                    if (role == button) {
                        role.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        role.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else {
                        role.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        role.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }

        val gradeButtons = listOf(binding.filterAttrGrade1, binding.filterAttrGrade2, binding.filterAttrGrade3)
        val gradeTexts = listOf("1", "2", "3")

        for (button in gradeButtons) {
            button.setOnClickListener {
                for (grade in gradeButtons) {
                    if (grade == button) {
                        grade.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        grade.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else {
                        grade.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        grade.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }

        val classButtons = listOf(binding.filterAttrClass1, binding.filterAttrClass2,
            binding.filterAttrClass3, binding.filterAttrClass4)
        val classTexts = listOf("1", "2", "3", "4")

        for (button in classButtons) {
            button.setOnClickListener {
                for (classNum in classButtons) {
                    if (classNum == button) {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        classNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    } else {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        classNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }
    }
}