package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import javax.inject.Inject

/**
 * Repository class responsible for handling authentication-related operations.
 * Acts as an abstraction layer between the data sources (FirebaseAuthService) and the ViewModel.
 * Follows the Repository pattern and is designed for use with Hilt dependency injection.
 *
 * @property authService The FirebaseAuthService instance used to perform authentication actions.
 */
class AuthRepository @Inject constructor(
    private val authService: FirebaseAuthService
) {
    /**
     * Registers a new user with the provided email and password.
     *
     * @param email The email address of the user to register.
     * @param password The password for the new user.
     * @return A [Result] containing the created [UserModel] on success, or an error on failure.
     */
    suspend fun signUpUser(email: String, password: String): Result<UserModel> {
        return authService.signUpUser(email, password)
    }

    /**
     * Signs in a user with the provided email and password.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return A [Result] containing the authenticated [UserModel] on success, or an error on failure.
     */
    suspend fun signInUser(email: String, password: String): Result<UserModel> {
        return authService.signInUser(email, password)
    }

    /**
     * Signs out the currently authenticated user.
     */
    fun signOutUser() {
        authService.signOutUser()
    }

    /**
     * Retrieves the currently logged-in user, if any.
     *
     * @return The [UserModel] of the current user, or null if no user is logged in.
     */
    fun getCurrentLoggedUser(): UserModel? {
        return authService.getCurrentLoggedUser()
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    fun checkUserIsLogged(): Boolean {
        return authService.checkUserIsLogged()
    }

}