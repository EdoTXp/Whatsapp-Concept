package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {

    suspend fun sendMessage(
        message: ChatModel,
        idUserRemitted: String,
        idUserReceived: String
    ): Result<Unit> {
        return firestore.sendMessage(message, idUserRemitted, idUserReceived)
    }

}