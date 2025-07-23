package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class responsible for handling message-related operations.
 * Acts as an abstraction layer between the data source (FirebaseFirestoreService)
 * and the rest of the application, following the Repository design pattern.
 *
 * @property firestore The FirebaseFirestoreService used to interact with Firestore.
 */
class MessageRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {
    /**
     * Sends a message from the remitted user to the received user.
     *
     * @param message The [MessageModel] to be sent.
     * @param idUserRemitted The ID of the user sending the message.
     * @param idUserReceived The ID of the user receiving the message.
     * @return [Result] indicating success or failure of the operation.
     */
    suspend fun sendMessage(
        message: MessageModel,
        idUserRemitted: String,
        idUserReceived: String
    ): Result<Unit> {
        return firestore.sendMessage(message, idUserRemitted, idUserReceived)
    }

    /**
     * Retrieves a flow of messages exchanged between two users.
     *
     * @param idUserRemitted The ID of the user who sent the messages.
     * @param idUserReceived The ID of the user who received the messages.
     * @return [Flow] emitting [Result] containing a list of [MessageModel]s.
     */
    fun getMessages(
        idUserRemitted: String,
        idUserReceived: String
    ): Flow<Result<List<MessageModel>>> {
        return firestore.getMessages(idUserRemitted, idUserReceived)
    }
}