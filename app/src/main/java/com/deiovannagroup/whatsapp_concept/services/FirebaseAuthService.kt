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
        try {
            val authResult = auth.createUserWithEmailAndPassword(
                email,
                password,
            ).await()

            val user = authResult.user
            if (user == null) {
                return Result.failure(Throwable("User not found"))
            }

            return Result.success(
                UserModel(
                    user.uid,
                    user.displayName ?: "",
                    user.email ?: "",
                    user.photoUrl.toString(),
                ),
            )

        } catch (e: FirebaseAuthWeakPasswordException) {
            return Result.failure(Throwable("Weak password: ${e.message}"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Result.failure(Throwable("Invalid email or password: ${e.message}"))
        } catch (e: FirebaseAuthUserCollisionException) {
            return Result.failure(Throwable("User already exists: ${e.message}"))
        } catch (e: FirebaseAuthException) {
            return Result.failure(Throwable("Authentication error: ${e.message}"))
        } catch (e: Exception) {
            return Result.failure(Throwable("Unknown error: ${e.message}"))
        }
    }

    suspend fun signInUser(email: String, password: String): Result<UserModel> {
        try {
            val loginResult = auth.signInWithEmailAndPassword(
                email,
                password,
            ).await()

            val user = loginResult.user
            if (user == null) {
                return Result.failure(Throwable("User not found"))
            }

            return Result.success(
                UserModel(
                    user.uid,
                    user.displayName ?: "",
                    user.email ?: "",
                    user.photoUrl.toString(),
                ),
            )
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Result.failure(Throwable("Invalid email or password: ${e.message}"))
        } catch (e: FirebaseAuthException) {
            return Result.failure(Throwable("Authentication error: ${e.message}"))
        } catch (e: Exception) {
            return Result.failure(Throwable("Unknown error: ${e.message}"))
        }
    }

    fun signOutUser() {
        auth.signOut()
    }

    fun getCurrentLoggedUser(): UserModel? {
        return auth.currentUser?.let {
            UserModel(
                it.uid,
                it.displayName ?: "",
                it.email ?: "",
                it.photoUrl.toString(),
            )
        }
    }

    fun checkUserIsLogged(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }
}