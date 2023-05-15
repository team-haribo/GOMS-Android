package com.goms.presentation.view.manage.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.presentation.R
import com.example.presentation.databinding.BottomSheetSearchFilterBinding
import com.goms.presentation.view.manage.StudentManageActivity
import com.goms.presentation.viewmodel.CouncilViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFilterBottomSheetDialog: BottomSheetDialogFragment() {
    private val councilViewModel by viewModels<CouncilViewModel>()

    private lateinit var binding: BottomSheetSearchFilterBinding
    private var changeRole = "ROLE_STUDENT"
    private var changeGrade = "1"
    private var changeClassNum = "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSearchFilterBinding.inflate(layoutInflater)

        bottomSheetLogic()
        binding.filterSearchButton.setOnClickListener {
            lifecycleScope.launch {
                councilViewModel.searchStudent(
                    grade = changeGrade.toInt(),
                    classNum = changeClassNum.toInt(),
                    name = binding.searchFilterEditText.text.toString(),
                    isBlackList = changeRole == "BLACK_LIST",
                    authority = setAuthority()
                )

                councilViewModel.searchStudent.collect { list ->
                    if (list != null) {
                        val activity = activity as StudentManageActivity
                        activity.setUserList(list)
                        dialog?.dismiss()
                    }
                }
            }
        }

        return binding.root
    }

    private fun setAuthority(): String {
        return if (changeRole == "BLACK_LIST")
            "ROLE_STUDENT"
        else changeRole
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

                        val currentIndex = roleButtons.indexOf(role)
                        changeRole = roleTexts[currentIndex]
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

                        val currentIndex = gradeButtons.indexOf(grade)
                        changeGrade = gradeTexts[currentIndex]
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

                        val currentIndex = classButtons.indexOf(classNum)
                        changeClassNum = classTexts[currentIndex]
                    } else {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        classNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }
    }
}