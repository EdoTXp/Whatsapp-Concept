package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<UserModel>>()
    val registerResult: LiveData<Result<UserModel>> = _registerResult

    fun signUp(email: String, password: String) {
       viewModelScope.launch {
           val result = repository.signUpUser(email, password)
           _registerResult.value = result
       }
    }
}