package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _authResult = MutableLiveData<Result<UserModel>>()
    val authResult: LiveData<Result<UserModel>> = _authResult

    private val _userResult = MutableLiveData<Result<Unit>>()
    val userResult: LiveData<Result<Unit>> = _userResult

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signUpUser(email, password)
            _authResult.value = result
        }
    }

    fun saveUser(id: String, name: String, email: String) {
        viewModelScope.launch {
            val user = UserModel(
                id,
                name,
                email,
                null,
            )
            _userResult.value = userRepository.saveUser(user)
        }
    }
}