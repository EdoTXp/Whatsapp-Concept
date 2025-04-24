package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService

class UserRepository(private val firestore: FirebaseFirestoreService) {
    suspend fun saveUser(user: UserModel): Result<Unit> {
        return firestore.saveUser(user)
    }
}