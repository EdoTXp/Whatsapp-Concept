package com.deiovannagroup.whatsapp_concept.repositories

import android.net.Uri
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class responsible for managing user-related data operations.
 * Acts as an abstraction layer between the data sources (Firestore, Storage) and the rest of the app.
 * Utilizes dependency injection for service classes.
 *
 * @property firestoreService Service for Firestore database operations.
 * @property storageService Service for Firebase Storage operations.
 */
class UserRepository @Inject constructor(
    private val firestoreService: FirebaseFirestoreService,
    private val storageService: FirebaseStorageService
) {
    /**
     * Saves a user to Firestore.
     *
     * @param user The [UserModel] to be saved.
     * @return [Result] containing [Unit] on success or an exception on failure.
     */
    suspend fun saveUser(user: UserModel): Result<Unit> {
        return firestoreService.saveUser(user)
    }

    /**
     * Retrieves the profile data for a given user.
     *
     * @param userId The ID of the user whose profile data is requested.
     * @return [Result] containing the [UserModel] on success or an exception on failure.
     */
    suspend fun getProfileData(userId: String): Result<UserModel> {
        return firestoreService.getProfileData(userId)
    }

    /**
     * Uploads a user image to Firebase Storage.
     *
     * @param imageUri The [Uri] of the image to upload.
     * @param userId The ID of the user uploading the image.
     * @return [Result] containing the [Uri] of the uploaded image on success or an exception on failure.
     */
    suspend fun uploadUserImage(imageUri: Uri, userId: String): Result<Uri> {
        return storageService.uploadUserImage(imageUri, userId)
    }

    /**
     * Retrieves the contact list for a given user as a Flow.
     *
     * @param userId The ID of the user whose contacts are requested.
     * @return [Flow] emitting [Result] with a list of [UserModel]s or an exception.
     */
    fun getContacts(userId: String): Flow<Result<List<UserModel>>> {
        return firestoreService.getContacts(userId)
    }

    /**
     * Updates the profile data for a given user.
     *
     * @param userId The ID of the user whose profile is to be updated.
     * @param data A map containing the profile fields to update.
     * @return [Result] containing [Unit] on success or an exception on failure.
     */
    fun updateProfile(userId: String, data: Map<String, String>): Result<Unit> {
        return firestoreService.updateProfile(userId, data)
    }
}