package com.deiovannagroup.whatsapp_concept.viewmodels


import androidx.lifecycle.ViewModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository

class MainViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun signOutUser() {
        authRepository.signOutUser()
    }
}