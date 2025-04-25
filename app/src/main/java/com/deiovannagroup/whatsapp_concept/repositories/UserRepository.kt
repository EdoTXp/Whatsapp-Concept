package com.deiovannagroup.whatsapp_concept.repositories

import android.net.Uri
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService

class UserRepository(
    private val firestoreService: FirebaseFirestoreService,
    private val storageService: FirebaseStorageService
) {
    suspend fun saveUser(user: UserModel): Result<Unit> {
        return firestoreService.saveUser(user)
    }

    suspend fun uploadUserImage(imageUri: Uri, userId: String): Result<Unit> {
        return storageService.uploadUserImage(imageUri, userId)
    }
}