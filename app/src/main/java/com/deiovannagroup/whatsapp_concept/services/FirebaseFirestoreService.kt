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
}