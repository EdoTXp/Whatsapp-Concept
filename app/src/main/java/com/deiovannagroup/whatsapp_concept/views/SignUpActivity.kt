package com.deiovannagroup.whatsapp_concept.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.ActivitySignUpBinding
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.utils.showMessage
import com.deiovannagroup.whatsapp_concept.viewmodels.SignUpViewModel
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val signUpViewModel by lazy {
        SignUpViewModel(
            AuthRepository(
                FirebaseAuthService(),
            ),
            UserRepository(
                FirebaseFirestoreService(),
            ),
        )
    }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()

        initToolbar()
        initListeners()

        addObserver()
    }

    private fun addObserver() {
        signUpViewModel.authResult.observe(this) { result ->
            result.onSuccess { user ->
                showMessage(getString(R.string.success_to_sign_up))

                signUpViewModel.saveUser(
                    user.id,
                    name,
                    email,
                )

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java,
                    ),
                )
            }
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_to_sign_up)}: ${error.message}",
                )
            }
        }

        signUpViewModel.userResult.observe(this) { result ->
            result.onSuccess {
                showMessage(
                    getString(R.string.success_to_sign_up),
                )
            }
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_to_sign_up)}: ${error.message}",
                )
            }
        }
    }

    private fun initToolbar() {
        val toolbar = binding.includeToolbar.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.sign_up)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initListeners() {
        binding.btnSignUp.setOnClickListener {
            if (validateAllFields())
                signUpViewModel.signUp(email, password)
        }
    }

    private fun validateAllFields(): Boolean {
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        val isNameValid = validateField(
            name,
            getString(R.string.empty_name),
            binding.textInputName,
        )
        val isEmailValid = validateField(
            email,
            getString(R.string.empty_email),
            binding.textInputEmail,
        )
        val isPasswordValid = validateField(
            password,
            getString(R.string.empty_password),
            binding.textInputPassword,
        )

        return isNameValid && isEmailValid && isPasswordValid
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