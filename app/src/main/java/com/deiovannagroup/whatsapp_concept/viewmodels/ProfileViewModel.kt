package com.deiovannagroup.whatsapp_concept.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.UserModel
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

    private val _uploadImageResult = MutableLiveData<Result<Uri>>()
    val uploadImageResult: LiveData<Result<Uri>> = _uploadImageResult

    private val _updateProfileResult = MutableLiveData<Result<Unit>>()
    val updateProfileResult: LiveData<Result<Unit>> = _updateProfileResult

    private val _profileData = MutableLiveData<Result<UserModel>>()
    val profileData: LiveData<Result<UserModel>> = _profileData

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            val loggedUser = authRepository.getCurrentLoggedUser()

            val id = loggedUser?.id

            if (id == null) {
                _uploadImageResult.value = Result.failure(
                    Exception("User not found"),
                )
            }

            _uploadImageResult.value = userRepository.uploadUserImage(
                uri,
                id!!,
            )
        }
    }

    fun getProfileData() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentLoggedUser()?.id

            if (userId == null) {
                _profileData.value = Result.failure(
                    Exception("User not found"),
                )
                return@launch
            }

            _profileData.value = userRepository.getProfileData(userId)
        }
    }

    fun updateProfile(data: Map<String, String>) {
        val userId = authRepository.getCurrentLoggedUser()?.id

        if (userId == null) {
            _updateProfileResult.value = Result.failure(
                Exception("User not found"),
            )
            return
        }

        _updateProfileResult.value = userRepository.updateProfile(userId, data)
    }
}