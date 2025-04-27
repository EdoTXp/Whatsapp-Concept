package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {
    suspend fun sendMessage(
        message: MessageModel,
        idUserRemitted: String,
        idUserReceived: String
    ): Result<Unit> {
        return firestore.sendMessage(message, idUserRemitted, idUserReceived)
    }

    fun getMessages(idUserRemitted: String, idUserReceived: String): Flow<Result<List<MessageModel>>> {
        return firestore.getMessages(idUserRemitted, idUserReceived)
    }
}