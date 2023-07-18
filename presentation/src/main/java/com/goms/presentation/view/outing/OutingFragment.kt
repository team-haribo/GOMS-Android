package com.goms.presentation.view.outing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.R
import com.goms.presentation.databinding.FragmentOutingBinding
import com.goms.presentation.utils.apiErrorHandling
import com.goms.presentation.view.outing.component.EmptyScreen
import com.goms.presentation.view.outing.component.OutingStudentCard
import com.goms.presentation.viewmodel.OutingViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class OutingFragment : Fragment() {
    private val outingViewModel by viewModels<OutingViewModel>()
    private lateinit var binding: FragmentOutingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOutingBinding.inflate(layoutInflater)

        binding.outingStudentSearchButton.setOnClickListener { view ->
            var inputText = binding.outingStudentSearch.text.toString()
            searchOutingStudentLogic(name = inputText)
        }
        outingLogic()

        return binding.root
    }

    private fun outingLogic() {
        setLoading()
        lifecycleScope.launch {
            apiErrorHandling(
                context = context,
                logic = { getOutingList() }
            )
        }
    }

    private fun getOutingList() {
        lifecycleScope.launch {
            outingViewModel.outingListLogic()
            outingViewModel.outingList.collect { list ->
                binding.outingStudentListLazyColumn.setContent {
                    if (list!!.isEmpty()) EmptyScreen()
                    else OutingLazyColumn(list)
                }
            }
        }
    }

    private fun searchOutingStudentLogic(name: String) {
        lifecycleScope.launch {
            outingViewModel.searchOutingStudent(name)
            outingViewModel.outingList.collect { list ->
                binding.outingStudentListLazyColumn.setContent {
                    if (list!!.isEmpty()) EmptyScreen()
                    else OutingLazyColumn(list)
                }
            }
        }
    }


    private fun setLoading() {
        lifecycleScope.launch {
            outingViewModel.isLoading.collect { loading ->
                if (loading) binding.outingLoadingIndicator.root.visibility = View.VISIBLE
                else binding.outingLoadingIndicator.root.visibility = View.GONE
            }
        }
    }

    private fun deleteOuting(accountIdx: UUID) {
        lifecycleScope.launch {
            apiErrorHandling(
                context = context,
                logic = { outingViewModel.deleteOuting(accountIdx) }
            )
            getOutingList()
        }
    }

    @Composable
    private fun OutingLazyColumn(list: List<UserResponseData>) {
        var inputText:String
        binding.outingStudentListLazyColumn.setContent {
            val isRefreshing by outingViewModel.isLoading.collectAsState()
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    inputText = binding.outingStudentSearch.text.toString()
                    searchOutingStudentLogic(name = inputText)
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colorResource(id = R.color.goms_main_color_student),
                    )
                }

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, top = 7.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    items(list) { item ->
                        Box(
                            modifier = Modifier.shadow(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                        ) {
                            OutingStudentCard(
                                item,
                                onClick = { UUID ->
                                    deleteOuting(UUID)
                                },
                                requireContext()
                            )
                        }
                    }
                }
            }
        }
    }

}