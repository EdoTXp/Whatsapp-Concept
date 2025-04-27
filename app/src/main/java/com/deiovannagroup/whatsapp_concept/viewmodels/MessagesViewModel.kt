package com.deiovannagroup.whatsapp_concept.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.repositories.ChatsRepository
import com.deiovannagroup.whatsapp_concept.repositories.MessageRepository
import com.deiovannagroup.whatsapp_concept.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatsRepository: ChatsRepository,
) : ViewModel() {

    private val _sendMessageResult = MutableLiveData<Result<Unit>>()
    val sendMessageResult: LiveData<Result<Unit>> = _sendMessageResult

    private val _messagesResult = MutableLiveData<Result<List<MessageModel>>>()
    val messagesResult: LiveData<Result<List<MessageModel>>> = _messagesResult
    private var getMessagesJob: Job? = null

    private val _sendChatResult = MutableLiveData<Result<Unit>>()
    val sendChatResult: LiveData<Result<Unit>> = _sendChatResult


    fun clearObserver() {
        getMessagesJob?.cancel()
    }

    fun getUserId(): String {
        return authRepository.getCurrentLoggedUser()?.id.toString()
    }

    suspend fun getLoggedUser(): UserModel? {
        val idUser = authRepository.getCurrentLoggedUser()?.id
        if (idUser == null) {
            return null
        }
        userRepository.getProfileData(idUser).onSuccess { user ->
            return user
        }
        return null
    }


    fun getMessages(idUserReceived: String?) {
        getMessagesJob = viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id
            if (idUserRemitted == null || idUserReceived == null) {
                _sendMessageResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            messageRepository.getMessages(
                idUserRemitted,
                idUserReceived,
            ).collect { result ->
                _messagesResult.value = result
            }
        }
    }

    fun sendMessage(message: String, idUserReceived: String?) {
        viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id

            if (idUserRemitted == null || idUserReceived == null) {
                _sendMessageResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            val message = MessageModel(
                idUserRemitted,
                message,
            )

            // Send message for remitter
            _sendMessageResult.value = messageRepository.sendMessage(
                message,
                idUserRemitted,
                idUserReceived,
            )

            // Send message for received
            _sendMessageResult.value = messageRepository.sendMessage(
                message,
                idUserReceived,
                idUserRemitted,
            )
        }
    }

    fun sendChat(
        idUserReceived: String?,
        photo: String,
        name: String,
        message: String,
    ) {
        viewModelScope.launch {
            val idUserRemitted = authRepository.getCurrentLoggedUser()?.id
            if (idUserRemitted == null || idUserReceived == null) {
                _sendChatResult.value = Result.failure(Exception("User not found"))
                return@launch
            }

            // Send chat for remitter
            val chatRemitter = ChatModel(
                idUserRemitted,
                idUserReceived,
                getLoggedUser()?.photo ?: "",
                name,
                message,
            )
            _sendChatResult.value = chatsRepository.sendChat(chatRemitter)

            // Send chat for received
            val chatReceived = ChatModel(
                idUserReceived,
                idUserRemitted,
                photo,
                name,
                message,
            )
            _sendChatResult.value = chatsRepository.sendChat(chatReceived)
        }
    }
}