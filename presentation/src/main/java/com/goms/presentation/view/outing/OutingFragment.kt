package com.goms.presentation.view.outing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.databinding.FragmentOutingBinding
import com.goms.presentation.utils.apiErrorHandling
import com.goms.presentation.view.outing.component.EmptyScreen
import com.goms.presentation.view.outing.component.OutingStudentCard
import com.goms.presentation.viewmodel.OutingViewModel
import com.goms.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class OutingFragment : Fragment() {
    private val outingViewModel by viewModels<OutingViewModel>()
    private lateinit var binding: FragmentOutingBinding
    private lateinit var role: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        setRole()
        binding = FragmentOutingBinding.inflate(layoutInflater)

        binding.outingStudentSearchButton.setOnClickListener { view ->
            var inputText = binding.outingStudentSearch.text.toString()
            searchOutingStudentLogic(name = inputText)
        }
        outingLogic()
        
        binding.refreshLayout.setOnRefreshListener {
            outingLogic()
            binding.refreshLayout.isRefreshing = false
        }

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

    private fun setRole() {
        val authorityPreferences = this.activity?.getSharedPreferences("authority", AppCompatActivity.MODE_PRIVATE)
        role = authorityPreferences?.getString("role", "").toString()
    }

    private suspend fun getOutingList() {
        outingViewModel.outingListLogic()
        outingViewModel.outingList.collect { list ->
            binding.outingStudentListLazyColumn.setContent {
                if (list!!.isEmpty()) EmptyScreen()
                else OutingLazyColumn(list)
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
        }
    }

    @Composable
    private fun OutingLazyColumn(list: List<UserResponseData>) {
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
                    )) {
                    OutingStudentCard(item, onClick = { UUID -> deleteOuting(UUID) }, role)
                }
            }
        }
    }
}