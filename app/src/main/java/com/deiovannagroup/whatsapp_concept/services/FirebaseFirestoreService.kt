package com.deiovannagroup.whatsapp_concept.services

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.utils.Constants.COLLECTION_CHATS
import com.deiovannagroup.whatsapp_concept.utils.Constants.COLLECTION_LAST_CHATS
import com.deiovannagroup.whatsapp_concept.utils.Constants.COLLECTION_MESSAGES
import com.deiovannagroup.whatsapp_concept.utils.Constants.COLLECTION_USERS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreService {
    private val firestore = FirebaseFirestore.getInstance()


    suspend fun saveUser(user: UserModel): Result<Unit> {
        try {
            firestore
                .collection(COLLECTION_USERS)
                .document(user.id)
                .set(user)
                .await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(
                Throwable(
                    "Error saving user: ${e.message}",
                ),
            )
        }
    }

    suspend fun getProfileData(userId: String): Result<UserModel> {
        try {
            val result = firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .get()
                .await()

            val userData = result.data

            if (userData == null) {
                return Result.failure(
                    Throwable("User data is null")
                )
            }

            val name = userData["name"] as String
            val photo = userData["photo"] as String
            val email = userData["email"] as String

            val user = UserModel(
                userId,
                name,
                email,
                photo,
            )

            return Result.success(user)
        } catch (e: Exception) {
            return Result.failure(
                Throwable(
                    "Error getting profile data: ${e.message}",
                ),
            )
        }
    }

    fun getContacts(userId: String): Flow<Result<List<UserModel>>> = callbackFlow {
        val listenerRegistration = firestore
            .collection(COLLECTION_USERS)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    trySend(Result.failure(Throwable("Error getting contacts: ${error.message}")))
                    return@addSnapshotListener
                }

                val users = mutableListOf<UserModel>()
                val documents = querySnapshot?.documents

                documents?.forEach { documentSnapshot ->
                    val user = documentSnapshot.toObject(UserModel::class.java)
                    if (user == null) {
                        return@forEach
                    }
                    if (user.id == userId) {
                        return@forEach
                    }

                    users.add(user)
                }

                trySend(Result.success(users))
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun getChats(userId: String): Flow<Result<List<ChatModel>>> = callbackFlow {
        val listenerRegistration =
            firestore.collection(COLLECTION_CHATS)
                .document(userId)
                .collection(COLLECTION_LAST_CHATS)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        trySend(Result.failure(Throwable("Error getting chats: ${error.message}")))
                        return@addSnapshotListener
                    }

                    val chats = mutableListOf<ChatModel>()
                    val documents = querySnapshot?.documents

                    documents?.forEach { documentSnapshot ->
                        val chat = documentSnapshot.toObject(ChatModel::class.java)
                        if (chat == null) {
                            return@forEach
                        }
                        chats.add(chat)
                    }

                    trySend(Result.success(chats))
                }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun updateProfile(userId: String, data: Map<String, String>): Result<Unit> {
        try {
            firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .update(data)

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(
                Throwable(
                    "Error updating profile: ${e.message}",
                ),
            )
        }
    }

    suspend fun sendMessage(
        message: MessageModel,
        idUserRemitted: String,
        idUserReceived: String,
    ): Result<Unit> {
        try {
            firestore
                .collection(COLLECTION_MESSAGES)
                .document(idUserRemitted)
                .collection(idUserReceived)
                .add(message)
                .await()



            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(
                Throwable(
                    "Error sending message: ${e.message}",
                ),
            )
        }
    }

    suspend fun sendChat(chat: ChatModel): Result<Unit> {
        try {
            firestore
                .collection(COLLECTION_CHATS)
                .document(chat.userIdRemitted)
                .collection(COLLECTION_LAST_CHATS)
                .document(chat.userIdReceived)
                .set(chat)
                .await()

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(
                Throwable(
                    "Error sending chat: ${e.message}",
                ),
            )
        }
    }

    fun getMessages(
        idUserRemitted: String,
        idUserReceived: String
    ): Flow<Result<List<MessageModel>>> =
        callbackFlow {
            val listenerRegistration =
                firestore
                    .collection(COLLECTION_MESSAGES)
                    .document(idUserRemitted)
                    .collection(idUserReceived)
                    .orderBy("date", Query.Direction.ASCENDING)
                    .addSnapshotListener { querySnapshot, error ->

                        if (error != null) {
                            trySend(
                                Result.failure(
                                    Throwable("Error getting chats: ${error.message}")
                                ),
                            )
                            return@addSnapshotListener
                        }

                        val chats = mutableListOf<MessageModel>()
                        val documents = querySnapshot?.documents

                        documents?.forEach { documentSnapshot ->
                            val chat = documentSnapshot.toObject(MessageModel::class.java)
                            if (chat == null) {
                                return@forEach
                            }
                            chats.add(chat)
                        }
                        trySend(Result.success(chats))
                    }
            awaitClose {
                listenerRegistration.remove()
            }
        }
}