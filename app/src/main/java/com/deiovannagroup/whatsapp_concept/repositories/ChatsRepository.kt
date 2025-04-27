package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatsRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {

    suspend fun sendChat(chat: ChatModel): Result<Unit> {
        return firestore.sendChat(chat)

    }

    fun getChats(userId: String): Flow<Result<List<ChatModel>>> {
        return firestore.getChats(userId)
    }
}