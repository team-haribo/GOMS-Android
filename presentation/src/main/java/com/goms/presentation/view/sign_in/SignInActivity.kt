package com.goms.presentation.view.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.goms.presentation.BuildConfig
import com.goms.presentation.R
import com.goms.presentation.databinding.ActivitySignInBinding
import com.goms.presentation.utils.apiErrorHandling
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.viewmodel.SignInViewModel
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.GAuthButton
import com.msg.gauthsignin.component.utils.Types
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginMainText.setContent { 
            SignInMainText()
        }
        
        binding.signInBtn.setContent {
            GAuthButton(
                style = Types.Style.DEFAULT,
                actionType = Types.ActionType.SIGNIN,
                colors = Types.Colors.COLORED,
                horizontalPaddingValue = 50.dp
            ) {
                binding.gauthWebView.visibility = View.VISIBLE
                setGAuthWebViewComponent()
            }
        }
    }

    @Composable
    private fun SignInMainText() {
        Text(
            buildAnnotatedString {
                append("간편한 ")
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.goms_main_color_student))
                ) {
                    append("수요 외출제")
                }
                append(" 서비스")
            },
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }

    private fun setGAuthWebViewComponent() {
        binding.gauthWebView.setContent {
            GAuthSigninWebView(
                clientId = BuildConfig.CLIENT_ID,
                redirectUri = BuildConfig.REDIRECT_URL,
            ) { code ->
                binding.gauthWebView.visibility = View.INVISIBLE

                signInViewModel.signInLogic(code)
                lifecycleScope.launch {
                    signInViewModel.isLoading.collect { loading ->
                        if (loading) {
                            binding.signInScreenView.visibility = View.GONE
                            binding.signInProgressBar.visibility = View.VISIBLE
                        } else {
                            binding.signInProgressBar.visibility = View.GONE
                            apiErrorHandling(
                                context = this@SignInActivity,
                                logic = { signInLogic() }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun signInLogic() {
        signInViewModel.signIn.collect { signInResponse ->
            if (signInResponse != null) {
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                signInViewModel.setAuthority(signInResponse.authority)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
        exitProcess(0)
    }
}