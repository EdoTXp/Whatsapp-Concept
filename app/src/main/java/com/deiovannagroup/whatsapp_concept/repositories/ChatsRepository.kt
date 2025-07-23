package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class responsible for managing chat-related operations.
 * Acts as an abstraction layer between the data sources (FirebaseFirestoreService) and the ViewModel.
 * Follows the Repository pattern and is designed for use with Hilt dependency injection.
 *
 * @property firestore The FirebaseFirestoreService instance used to perform chat actions.
 */
class ChatsRepository @Inject constructor(
    private val firestore: FirebaseFirestoreService
) {

    /**
     * Sends a chat message using the provided [ChatModel].
     *
     * @param chat The [ChatModel] representing the chat message to be sent.
     * @return A [Result] indicating success or failure of the operation.
     */
    suspend fun sendChat(chat: ChatModel): Result<Unit> {
        return firestore.sendChat(chat)
    }

    /**
     * Retrieves a flow of chat messages for the specified user.
     *
     * @param userId The ID of the user whose chats are to be retrieved.
     * @return A [Flow] emitting [Result]s containing lists of [ChatModel]s.
     */
    fun getChats(userId: String): Flow<Result<List<ChatModel>>> {
        return firestore.getChats(userId)
    }
}