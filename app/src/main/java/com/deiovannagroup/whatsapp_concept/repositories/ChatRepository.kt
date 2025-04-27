package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {

   suspend fun sendChat(chat: ChatModel) :Result<Unit> {
        return firestore.sendChat(chat)

    }
}