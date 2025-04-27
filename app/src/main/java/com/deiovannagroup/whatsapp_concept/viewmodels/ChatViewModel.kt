package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _messageResult = MutableLiveData<Result<Unit>>()
    val messageResult: LiveData<Result<Unit>> = _messageResult

    private val _chatsResult = MutableLiveData<Result<List<ChatModel>>>()
    val chatsResult: LiveData<Result<List<ChatModel>>> = _chatsResult
    private var getChatsJob: Job? = null

    fun clearObserver() {
        getChatsJob?.cancel()
    }

    fun getUserId(): String {
        return authRepository.getCurrentLoggedUser()?.id.toString()
    }

    fun getChats(idUserReceived: String?) {
        getChatsJob = viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id
            if (idUserRemitted == null || idUserReceived == null) {
                _messageResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            chatRepository.getChats(
                idUserRemitted,
                idUserReceived,
            ).collect { result ->
                _chatsResult.value = result
            }
        }
    }

    fun sendMessage(chat: String, idUserReceived: String?) {
        viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id

            if (idUserRemitted == null || idUserReceived == null) {
                _messageResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            val chat = ChatModel(
                idUserRemitted,
                chat,
            )

            // Send message for remitter
            _messageResult.value = chatRepository.sendMessage(
                chat,
                idUserRemitted,
                idUserReceived,
            )

            // Send message for received
            _messageResult.value = chatRepository.sendMessage(
                chat,
                idUserReceived,
                idUserRemitted,
            )
        }
    }
}