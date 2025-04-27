package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _chatResult = MutableLiveData<Result<Unit>>()
    val chatResult: LiveData<Result<Unit>> = _chatResult

    fun sendMessage(chat: String, idRemitted: String?) {
        viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id

            if (idUserRemitted == null || idRemitted == null) {
                _chatResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            val chat = ChatModel(
                idUserRemitted,
                chat,
            )

            _chatResult.value = chatRepository.sendMessage(
                chat,
                idUserRemitted,
                idRemitted,
            )

            _chatResult.value = chatRepository.sendMessage(
                chat,
                idRemitted,
                idUserRemitted,
            )
        }
    }
}