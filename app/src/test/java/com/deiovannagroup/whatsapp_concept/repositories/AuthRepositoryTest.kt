package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class AuthRepositoryTest {

    private lateinit var authService: FirebaseAuthService
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authService = mock()
        authRepository = AuthRepository(authService)
    }

    @Test
    fun `signUpUser returns success result when registration is successful`() = runBlocking {
        val user = UserModel(id = "1", name = "Test", email = "test@email.com", photo = "")
        `when`(authService.signUpUser(any(), any())).thenReturn(Result.success(user))
        val result = authRepository.signUpUser("test@email.com", "password")
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `signUpUser returns failure result when registration fails`() = runBlocking {
        val exception = Exception("Registration failed")
        `when`(authService.signUpUser(any(), any())).thenReturn(Result.failure(exception))
        val result = authRepository.signUpUser("fail@email.com", "password")
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `signInUser returns success result when authentication is successful`() = runBlocking {
        val user = UserModel(id = "2", name = "User", email = "user@email.com", photo = "")
        `when`(authService.signInUser(any(), any())).thenReturn(Result.success(user))
        val result = authRepository.signInUser("user@email.com", "password")
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `signInUser returns failure result when authentication fails`() = runBlocking {
        val exception = Exception("Authentication failed")
        `when`(authService.signInUser(any(), any())).thenReturn(Result.failure(exception))
        val result = authRepository.signInUser("fail@email.com", "password")
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `signOutUser calls authService signOutUser`() {
        authRepository.signOutUser()
        verify(authService).signOutUser()
    }

    @Test
    fun `getCurrentLoggedUser returns current user when logged in`() {
        val user = UserModel(id = "3", name = "Current", email = "current@email.com", photo = "")
        `when`(authService.getCurrentLoggedUser()).thenReturn(user)
        val result = authRepository.getCurrentLoggedUser()
        assertEquals(user, result)
    }

    @Test
    fun `getCurrentLoggedUser returns null when no user is logged in`() {
        `when`(authService.getCurrentLoggedUser()).thenReturn(null)
        val result = authRepository.getCurrentLoggedUser()
        assertNull(result)
    }

    @Test
    fun `checkUserIsLogged returns true when user is logged in`() {
        `when`(authService.checkUserIsLogged()).thenReturn(true)
        val result = authRepository.checkUserIsLogged()
        assertTrue(result)
    }

    @Test
    fun `checkUserIsLogged returns false when no user is logged in`() {
        `when`(authService.checkUserIsLogged()).thenReturn(false)
        val result = authRepository.checkUserIsLogged()
        assertFalse(result)
    }
}