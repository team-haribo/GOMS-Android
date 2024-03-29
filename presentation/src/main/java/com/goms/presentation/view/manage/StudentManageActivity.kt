package com.goms.presentation.view.manage

import android.os.Bundle
import android.view.View
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.goms.domain.data.council.response.UserInfoResponseData
import com.goms.presentation.R
import com.goms.presentation.databinding.ActivityStudentManageBinding
import com.goms.presentation.utils.dialog.GomsDialog
import com.goms.presentation.view.manage.bottomsheet.ModifyRoleBottomSheetDialog
import com.goms.presentation.view.manage.bottomsheet.SearchFilterBottomSheetDialog
import com.goms.presentation.view.manage.component.SearchResultEmptyScreen
import com.goms.presentation.view.manage.component.StudentManageCard
import com.goms.presentation.view.manage.data.UserFilterOptions
import com.goms.presentation.viewmodel.CouncilViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentManageActivity : AppCompatActivity() {
    private val councilViewModel by viewModels<CouncilViewModel>()
    private lateinit var binding: ActivityStudentManageBinding

    private lateinit var searchFilterBottomSheetDialogBinding: SearchFilterBottomSheetDialog
    private lateinit var bottomSheetModifyRoleDialog: ModifyRoleBottomSheetDialog

    private var userFilterOptions: UserFilterOptions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentManageLogic()
        binding.manageStudentSearchView.setOnClickListener {
            searchFilterBottomSheetDialogBinding = SearchFilterBottomSheetDialog()
            searchFilterBottomSheetDialogBinding.show(supportFragmentManager, searchFilterBottomSheetDialogBinding.tag)
        }

        binding.studentManageBackArrowImage.setOnClickListener { finish() }
    }

    private fun studentManageLogic() {
        setLoading()
        lifecycleScope.launch {
            if (userFilterOptions != null) refreshSearchUser(userFilterOptions!!)
            else getUserList()
        }
    }

    private fun setLoading() {
        lifecycleScope.launch {
            councilViewModel.isLoading.collect { loading ->
                if (loading) binding.manageStudentLoadingIndicator.root.visibility = View.VISIBLE
                else binding.manageStudentLoadingIndicator.root.visibility = View.GONE
            }
        }
    }

    private suspend fun getUserList() {
        councilViewModel.getUserList(activity = this)
        councilViewModel.userList.collect { list ->
            if (list != null) initUserList(list)
        }
    }

    private fun initUserList(list: List<UserInfoResponseData>) {
        binding.manageStudentStudentList.setContent {
            val isRefreshing by councilViewModel.isLoading.collectAsState()

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = { studentManageLogic() },
                indicator = { state, refreshTrigger ->  
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colorResource(id = R.color.goms_main_color_admin)
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(2.dp)
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
                                    if (item.isBlackList) {
                                        val dialog = GomsDialog(
                                            title = "외출 금지 해제",
                                            content = "해당 학생의 외출 금지를 취소하시겠습니까?",
                                            accountIdx = uuid
                                        )
                                        dialog.show(supportFragmentManager, "cancelBlackList")
                                    } else {
                                        bottomSheetModifyRoleDialog = ModifyRoleBottomSheetDialog(uuid, item)
                                        bottomSheetModifyRoleDialog.show(supportFragmentManager, bottomSheetModifyRoleDialog.tag)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun searchUserList(list: List<UserInfoResponseData>) {
        binding.manageStudentStudentList.setContent {
            val isRefreshResult by councilViewModel.isLoading.collectAsState()

            if (list.isEmpty()) SearchResultEmptyScreen()
            else {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshResult),
                    onRefresh = { studentManageLogic() },
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            contentColor = colorResource(id = R.color.goms_main_color_admin)
                        )
                    }
                ) {
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
                                    item = UserInfoResponseData(
                                        accountIdx = item.accountIdx,
                                        name = item.name,
                                        studentNum = item.studentNum,
                                        profileUrl = item.profileUrl,
                                        authority = item.authority,
                                        isBlackList = item.isBlackList
                                    ),
                                    iconClick = { uuid ->
                                        bottomSheetModifyRoleDialog = ModifyRoleBottomSheetDialog(uuid, item)
                                        bottomSheetModifyRoleDialog.show(supportFragmentManager, bottomSheetModifyRoleDialog.tag)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun refreshSearchUser(options: UserFilterOptions) {
        lifecycleScope.launch {
            options.apply {
                councilViewModel.searchStudent(
                    grade = grade,
                    classNum = classNum,
                    name = name,
                    isBlackList = isBlackList,
                    authority = authority,
                    activity = this@StudentManageActivity
                )
            }

            councilViewModel.searchStudent.collect { list ->
                if (list != null) searchUserList(list)
            }
        }
    }

    fun saveFilterOptions(
        grade: Int?,
        classNum: Int?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ) {
        userFilterOptions = UserFilterOptions(grade = grade, classNum = classNum, name = name, isBlackList = isBlackList, authority = authority)
    }
}
