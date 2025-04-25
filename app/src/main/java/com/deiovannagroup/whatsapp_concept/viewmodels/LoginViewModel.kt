package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<UserModel>>()
    val loginResult: LiveData<Result<UserModel>> = _loginResult

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signInUser(email, password)
            _loginResult.value = result
        }
    }

    fun checkUserIsLogged() = authRepository.checkUserIsLogged()

}