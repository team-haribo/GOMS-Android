package com.goms.presentation.view.manage

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.presentation.R
import com.example.presentation.databinding.ActivityStudentManageBinding
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.view.manage.component.StudentManageCard
import com.goms.presentation.view.manage.component.StudentManageSearchTextField
import com.goms.presentation.viewmodel.CouncilViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentManageActivity : AppCompatActivity() {
    private val councilViewModel by viewModels<CouncilViewModel>()
    private lateinit var binding: ActivityStudentManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            councilViewModel.getUserList()
            councilViewModel.userList.collect { list ->
                if (list != null) {
                    setUserList(list)
                }
            }
        }

        val bottomSheet = binding.bottomSheetView
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.manageStudentSearchView.setOnClickListener {
            binding.manageStudentBottomSheetView.visibility = View.VISIBLE
            binding.modifyRoleBottomSheetView.visibility = View.GONE

            setBottomSheet(behavior)
        }
        bottomSheetLogic()

        binding.filterSearchView.setContent {
            var textValue by remember { mutableStateOf("") }
            StudentManageSearchTextField(
                hint = "찾으시는 학생이 있으신가요?",
                value = textValue,
                onValueChange = { textValue = it }
            )
        }

        binding.studentManageBackArrowImage.setOnClickListener { finish() }
    }

    fun setBottomSheet(behavior: BottomSheetBehavior<LinearLayout>) {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING && newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    behavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun setUserList(list: List<UserResponseData>) {
        binding.manageStudentStudentList.setContent {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 2.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                // 임시 데이터
                items(list) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        StudentManageCard(
                            binding = binding,
                            activity = this@StudentManageActivity,
                            item = item
                        )
                    }
                }
            }
        }
    }

    private fun bottomSheetLogic() {
        // state를 보관할 변수 선언하기
        val roleButtons = listOf(binding.filterAttrStudent, binding.filterAttrCouncil, binding.filterAttrBlacklist)
        val roleTexts = listOf("ROLE_STUDENT", "ROLE_STUDENT_COUNCIL", "BLACK_LIST")

        for (button in roleButtons) {
            button.setOnClickListener {
                for (role in roleButtons) {
                    if (role == button) {
                        role.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        role.setTextColor(ContextCompat.getColor(this, R.color.white))
                    } else {
                        role.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        role.setTextColor(ContextCompat.getColor(this, R.color.goms_second_color_gray))
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
                        grade.setTextColor(ContextCompat.getColor(this, R.color.white))
                    } else {
                        grade.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        grade.setTextColor(ContextCompat.getColor(this, R.color.goms_second_color_gray))
                    }
                }
            }
        }

        val classButtons = listOf(binding.filterAttrClass1, binding.filterAttrClass2, binding.filterAttrClass3, binding.filterAttrClass4)
        val classTexts = listOf("1", "2", "3", "4")

        for (button in classButtons) {
            button.setOnClickListener {
                for (classNum in classButtons) {
                    if (classNum == button) {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        classNum.setTextColor(ContextCompat.getColor(this, R.color.white))
                    } else {
                        classNum.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        classNum.setTextColor(ContextCompat.getColor(this, R.color.goms_second_color_gray))
                    }
                }
            }
        }

        val modifyRoleButtons = listOf(binding.modifyFilterAttrStudent, binding.modifyFilterAttrCouncil, binding.modifyFilterAttrBlacklist)
        val modifyRoleTexts = listOf("ROLE_STUDENT", "ROLE_STUDENT_COUNCIL", "BLACK_LIST")

        for (button in modifyRoleButtons) {
            button.setOnClickListener {
                for (modifyRole in modifyRoleButtons) {
                    if (modifyRole == button) {
                        modifyRole.setBackgroundResource(R.drawable.admin_attribute_button_selected)
                        modifyRole.setTextColor(ContextCompat.getColor(this, R.color.white))
                    } else {
                        modifyRole.setBackgroundResource(R.drawable.admin_attribute_button_unselected)
                        modifyRole.setTextColor(ContextCompat.getColor(this, R.color.goms_second_color_gray))
                    }
                }
            }
        }
    }
}