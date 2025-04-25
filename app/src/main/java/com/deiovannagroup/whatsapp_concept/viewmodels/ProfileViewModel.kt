package com.deiovannagroup.whatsapp_concept.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uploadResult = MutableLiveData<Result<Unit>>()
    val uploadResult: LiveData<Result<Unit>> = _uploadResult


    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            val loggedUser = authRepository.getCurrentLoggedUser()

            val id = loggedUser?.id

            if (id == null) {
                _uploadResult.value = Result.failure(
                    Exception("User not found"),
                )
            }

            _uploadResult.value = userRepository.uploadUserImage(
                uri,
                id!!,
            )
        }
    }
}