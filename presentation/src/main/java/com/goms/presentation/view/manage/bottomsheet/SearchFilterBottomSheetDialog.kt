package com.goms.presentation.view.manage.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.goms.presentation.R
import com.goms.presentation.databinding.BottomSheetSearchFilterBinding
import com.goms.presentation.view.manage.StudentManageActivity
import com.goms.presentation.viewmodel.CouncilViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFilterBottomSheetDialog: BottomSheetDialogFragment() {
    private val councilViewModel by viewModels<CouncilViewModel>()

    private lateinit var binding: BottomSheetSearchFilterBinding
    private var changeRole: String? = null
    private var changeGrade: Int? = null
    private var changeClassNum: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSearchFilterBinding.inflate(layoutInflater)

        bottomSheetLogic()
        binding.filterSearchButton.setOnClickListener {
            val name = binding.searchFilterEditText.text.toString()
            lifecycleScope.launch {
                councilViewModel.searchStudent(
                    grade = changeGrade,
                    classNum = changeClassNum,
                    name = name.ifEmpty { null },
                    isBlackList = setIsBlackList(),
                    authority = setAuthority()
                )

                councilViewModel.searchStudent.collect { list ->
                    if (list != null) {
                        val activity = activity as StudentManageActivity
                        activity.searchUserList(list)
                        dialog?.dismiss()
                    }
                }
            }
        }

        return binding.root
    }

    private fun setIsBlackList(): Boolean? {
        return when (changeRole) {
            "BLACK_LIST" -> true
            "ROLE_STUDENT", "ROLE_STUDENT_COUNCIL" -> false
            else -> null
        }
    }

    private fun setAuthority(): String? {
        return if (changeRole == "BLACK_LIST") null
        else changeRole
    }

    private fun bottomSheetLogic() {
        val roleButtons = listOf(binding.filterAttrStudent, binding.filterAttrCouncil, binding.filterAttrBlacklist)
        val roleTexts = listOf("ROLE_STUDENT", "ROLE_STUDENT_COUNCIL", "BLACK_LIST")

        val gradeButtons = listOf(binding.filterAttrGrade1, binding.filterAttrGrade2, binding.filterAttrGrade3)
        val gradeTexts = listOf("1", "2", "3")

        val classButtons = listOf(binding.filterAttrClass1, binding.filterAttrClass2, binding.filterAttrClass3, binding.filterAttrClass4)
        val classTexts = listOf("1", "2", "3", "4")

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

        for (button in gradeButtons) {
            button.setOnClickListener {
                for (grade in gradeButtons) {
                    if (grade == button) {
                        grade.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        grade.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                        val currentIndex = gradeButtons.indexOf(grade)
                        changeGrade = gradeTexts[currentIndex].toInt()
                    } else {
                        grade.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        grade.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }

        for (button in classButtons) {
            button.setOnClickListener {
                for (classNum in classButtons) {
                    if (classNum == button) {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        classNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                        val currentIndex = classButtons.indexOf(classNum)
                        changeClassNum = classTexts[currentIndex].toInt()
                    } else {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        classNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }

        val buttonList = listOf(roleButtons, gradeButtons, classButtons)
        clearButtonLogic(buttonList)
    }

    private fun clearButtonLogic(list: List<List<AppCompatButton>>) {
        binding.filterResetImage.setOnClickListener {
            changeRole = null
            changeGrade = null
            changeClassNum = null

            clearButtonColor(list)
        }
    }

    private fun clearButtonColor(buttonList: List<List<AppCompatButton>>) {
        for (list in buttonList) {
            for (button in list) {
                for (item in list) {
                    if (item == button) {
                        item.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        item.setTextColor(ContextCompat.getColor(requireContext(), R.color.goms_second_color_gray))
                    }
                }
            }
        }
    }
}