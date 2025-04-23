package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService


class AuthRepository(
    private val authService: FirebaseAuthService
) {
    suspend fun signUpUser(email: String, password: String): Result<UserModel> {
        return authService.signUpUser(email, password)
    }
}