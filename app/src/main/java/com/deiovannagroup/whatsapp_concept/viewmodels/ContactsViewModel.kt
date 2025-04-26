package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _contactsResult = MutableLiveData<Result<List<UserModel>>>()
    val contactsResult: LiveData<Result<List<UserModel>>> = _contactsResult
    private var getContactsJob: Job? = null

    fun clearObserver() {
        getContactsJob?.cancel()
    }

    fun getContacts() {
        getContactsJob = viewModelScope.launch {
            val userId = authRepository.getCurrentLoggedUser()?.id

            if (userId == null) {
                _contactsResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            userRepository.getContacts(userId).collect { result ->
                _contactsResult.value = result
            }
        }
    }
}