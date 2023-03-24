package com.example.gsm_goms.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.gsm_goms.databinding.ActivitySignInBinding
import com.example.gsm_goms.presentation.viewmodel.SignInViewModel
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.GAuthButton
import com.msg.gauthsignin.component.utils.Types
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInBtn.setContent {
            GAuthButton(
                style = Types.Style.DEFAULT,
                actionType = Types.ActionType.CONTINUE,
                colors = Types.Colors.OUTLINE,
                horizontalPaddingValue = 50.dp
            ) {
                binding.gauthWebView.visibility = View.VISIBLE
                setGAuthWebViewComponent()
            }
        }
    }

    private fun setGAuthWebViewComponent() {
        binding.gauthWebView.setContent {
            GAuthSigninWebView(
                clientId = "c6731e83059f4decaaa5b6a79c75320c306471f896da4284811f02bdcfeb7f94",
                redirectUri = "https://port-0-goms-backend-nx562olfamh7iw.sel3.cloudtype.app/",
            ) { code ->
                binding.gauthWebView.visibility = View.INVISIBLE

                viewModel.signInLogic(code)
                lifecycleScope.launch {
                    viewModel.signIn.collect { signInResponse ->
                        Log.d("TAG", "setGAuthWebViewComponent: $signInResponse")
                        if (signInResponse != null) {
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java)
                                .putExtra("accessToken", signInResponse.accessToken)
                                .putExtra("refreshToken", signInResponse.refreshToken))
                        }
                    }
                }
            }
        }
    }
}