package com.deiovannagroup.whatsapp_concept.repositories

import android.net.Uri
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestoreService: FirebaseFirestoreService,
    private val storageService: FirebaseStorageService
) {
    suspend fun saveUser(user: UserModel): Result<Unit> {
        return firestoreService.saveUser(user)
    }

    suspend fun getProfileData(userId: String): Result<UserModel> {
        return firestoreService.getProfileData(userId)
    }

    suspend fun uploadUserImage(imageUri: Uri, userId: String): Result<Uri> {
        return storageService.uploadUserImage(imageUri, userId)
    }

    fun getContacts(userId: String): Flow<Result<List<UserModel>>> {
        return firestoreService.getContacts(userId)
    }

    fun updateProfile(userId: String, data: Map<String, String>): Result<Unit> {
        return firestoreService.updateProfile(userId, data)
    }


}