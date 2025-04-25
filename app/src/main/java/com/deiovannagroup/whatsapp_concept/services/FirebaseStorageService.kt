package com.deiovannagroup.whatsapp_concept.services

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageService {
    private val storage = FirebaseStorage.getInstance()

    companion object {
        private const val LOCATION = "photos"
        private const val USER_PATH = "users"
        private const val PROFILE_IMAGE = "profile.jpg"
    }

    suspend fun uploadUserImage(uri: Uri, userId: String): Result<Uri> {
        try {
            val result = storage
                .getReference(LOCATION)
                .child(USER_PATH)
                .child(userId)
                .child(PROFILE_IMAGE)
                .putFile(uri)
                .await()

            val url = result.metadata
                ?.reference
                ?.downloadUrl
                ?.result

            if (url == null) {
                return Result.failure(Throwable("Something went wrong"))
            }

            return Result.success(url)
        } catch (e: Exception) {
            return Result.failure(Throwable(e.message))
        }
    }

}