package com.deiovannagroup.whatsapp_concept.viewmodels


import androidx.lifecycle.ViewModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun signOutUser() {
        authRepository.signOutUser()
    }
}