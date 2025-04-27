package com.deiovannagroup.whatsapp_concept.services

import android.net.Uri
import com.deiovannagroup.whatsapp_concept.utils.Constants.LOCATION
import com.deiovannagroup.whatsapp_concept.utils.Constants.PROFILE_IMAGE
import com.deiovannagroup.whatsapp_concept.utils.Constants.USER_PATH
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageService {
    private val storage = FirebaseStorage.getInstance()

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