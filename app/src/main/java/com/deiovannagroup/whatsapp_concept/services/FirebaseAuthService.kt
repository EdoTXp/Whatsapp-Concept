package com.deiovannagroup.whatsapp_concept.services

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

/**
 * Service class responsible for handling Firebase Authentication operations.
 * Provides methods for user sign up, sign in, sign out, and user session management.
 */
class FirebaseAuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email The email address of the user to register.
     * @param password The password for the new user.
     * @return [Result] containing the created [UserModel] on success, or an error on failure.
     */
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

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email The email address of the user to sign in.
     * @param password The password for the user.
     * @return [Result] containing the authenticated [UserModel] on success, or an error on failure.
     */
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

    /**
     * Signs out the currently authenticated user.
     */
    fun signOutUser() {
        auth.signOut()
    }

    /**
     * Retrieves the currently logged-in user as a [UserModel].
     *
     * @return The [UserModel] of the current user, or null if no user is logged in.
     */
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

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    fun checkUserIsLogged(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }
}