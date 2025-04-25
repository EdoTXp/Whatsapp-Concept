package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authService: FirebaseAuthService
) {
    suspend fun signUpUser(email: String, password: String): Result<UserModel> {
        return authService.signUpUser(email, password)
    }

    suspend fun signInUser(email: String, password: String): Result<UserModel> {
        return authService.signInUser(email, password)
    }

    fun signOutUser() {
        authService.signOutUser()
    }

    fun getCurrentLoggedUser(): UserModel? {
        return authService.getCurrentLoggedUser()
    }

    fun checkUserIsLogged(): Boolean {
        return authService.checkUserIsLogged()
    }

}