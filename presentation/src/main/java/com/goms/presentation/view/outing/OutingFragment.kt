package com.goms.presentation.view.outing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.presentation.databinding.FragmentOutingBinding
import com.goms.presentation.view.outing.component.EmptyScreen
import com.goms.presentation.view.outing.component.OutingStudentCard

class OutingFragment : Fragment() {
    private lateinit var binding: FragmentOutingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentOutingBinding.inflate(layoutInflater)

        binding.outingStudentListLazyColumn.setContent {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            if (list.isEmpty()) EmptyScreen()
            else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, top = 7.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    items(list) {
                        Box(modifier = Modifier.shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(10.dp)
                        )) {
                            OutingStudentCard()
                        }
                    }
                }
            }
        }

        return binding.root
    }
}