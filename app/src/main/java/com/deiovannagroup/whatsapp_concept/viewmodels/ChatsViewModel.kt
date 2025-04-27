package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.ChatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _chatsResult = MutableLiveData<Result<List<ChatModel>>>()
    val chatsResult: MutableLiveData<Result<List<ChatModel>>> = _chatsResult
    private var getChatsJob: Job? = null

    fun clearObserver() {
        getChatsJob?.cancel()
    }

    fun getChats() {
        getChatsJob = viewModelScope.launch {
            val userId = authRepository.getCurrentLoggedUser()?.id

            if (userId == null) {
                _chatsResult.value = Result.failure(Exception("User not logged in"))
                return@launch
            }

            chatsRepository.getChats(userId).collect { result ->
                _chatsResult.value = result
            }
        }
    }
}