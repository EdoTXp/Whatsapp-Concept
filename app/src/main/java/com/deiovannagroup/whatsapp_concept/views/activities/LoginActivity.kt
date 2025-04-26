package com.deiovannagroup.whatsapp_concept.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.ActivityLoginBinding
import com.deiovannagroup.whatsapp_concept.utils.showMessage
import com.deiovannagroup.whatsapp_concept.viewmodels.LoginViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        initListeners()

        addObservers()
    }

    override fun onStart() {
        super.onStart()
        if (loginViewModel.checkUserIsLogged()) {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java,
                ),
            )
        }
    }

    override fun onStop() {
        loginViewModel.loginResult.removeObservers(this)
        super.onStop()
    }

    private fun addObservers() {
        loginViewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                showMessage(
                    getString(R.string.user_logged_successfully),
                )

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java,
                    )
                )
            }
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.user_not_logged)}: ${error.message}",
                )
            }
        }
    }

    private fun initListeners() {
        binding.textSignUp.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SignUpActivity::class.java,
                )
            )
        }

        binding.btnLogin.setOnClickListener {
            if (validateAllFields()) {
                loginViewModel.signInUser(
                    email,
                    password,
                )
            }
        }
    }

    private fun validateAllFields(): Boolean {
        email = binding.editLoginEmail.text.toString().trim()
        password = binding.editLoginPassword.text.toString().trim()


        val isEmailValid = validateField(
            email,
            getString(R.string.empty_email),
            binding.textInputLoginEmail,
        )
        val isPasswordValid = validateField(
            password,
            getString(R.string.empty_password),
            binding.textInputLoginPassword,
        )

        return isEmailValid && isPasswordValid
    }

    private fun validateField(
        value: String,
        errorMessage: String,
        layout: TextInputLayout,
    ): Boolean {
        return if (value.isEmpty()) {
            layout.error = errorMessage
            false
        } else {
            layout.error = null
            true
        }
    }

    private fun setEdgeToEdgeLayout() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
    }
}