package com.deiovannagroup.whatsapp_concept.services

import com.deiovannagroup.whatsapp_concept.models.UserModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await


class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signUpUser(email: String, password: String): Result<UserModel> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(
                email,
                password,
            ).await()

            val user = authResult.user
            if (user == null) {
                return Result.failure(Throwable("User not found"))
            }

            val email = user.email
            if (email == null) {
                return Result.failure(Throwable("User email not found"))
            }

            return Result.success(UserModel(null, email))

        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Throwable("Weak password: ${e.message}"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Throwable("Invalid email or password: ${e.message}"))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Throwable("User already exists: ${e.message}"))
        } catch (e: FirebaseAuthException) {
            Result.failure(Throwable("Authentication error: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(Throwable("Unknown error: ${e.message}"))
        }
    }
}