package com.deiovannagroup.whatsapp_concept.services

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreService {
    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_USERS = "users"
    }

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
}