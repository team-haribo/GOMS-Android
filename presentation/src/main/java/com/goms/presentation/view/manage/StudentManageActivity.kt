package com.goms.presentation.view.manage

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.presentation.databinding.ActivityStudentManageBinding
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.view.manage.bottomsheet.FilterBottomSheetDialog
import com.goms.presentation.view.manage.bottomsheet.ModifyRoleBottomSheetDialog
import com.goms.presentation.view.manage.component.StudentManageCard
import com.goms.presentation.viewmodel.CouncilViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentManageActivity : AppCompatActivity() {
    private val councilViewModel by viewModels<CouncilViewModel>()
    private lateinit var binding: ActivityStudentManageBinding

    private lateinit var filterBottomSheetDialogBinding: FilterBottomSheetDialog
    private lateinit var bottomSheetModifyRoleDialog: ModifyRoleBottomSheetDialog

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

        binding.manageStudentSearchView.setOnClickListener {
            filterBottomSheetDialogBinding = FilterBottomSheetDialog()
            filterBottomSheetDialogBinding.show(supportFragmentManager, filterBottomSheetDialogBinding.tag)
        }

        binding.studentManageBackArrowImage.setOnClickListener { finish() }
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
                items(list) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                    ) {
                        StudentManageCard(
                            item = item,
                            iconClick = { uuid ->
                                bottomSheetModifyRoleDialog = ModifyRoleBottomSheetDialog(uuid)
                                bottomSheetModifyRoleDialog.show(supportFragmentManager, bottomSheetModifyRoleDialog.tag)
                            }
                        )
                    }
                }
            }
        }
    }
}