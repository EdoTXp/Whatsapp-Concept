package com.deiovannagroup.whatsapp_concept.repositories

import android.net.Uri
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import com.deiovannagroup.whatsapp_concept.services.FirebaseStorageService
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class UserRepositoryTest {

    private lateinit var firestoreService: FirebaseFirestoreService
    private lateinit var storageService: FirebaseStorageService
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        firestoreService = mock()
        storageService = mock()
        userRepository = UserRepository(firestoreService, storageService)
    }

    @Test
    fun `saveUser returns success when firestoreService returns success`() = runBlocking {
        val user = UserModel(id = "1", name = "Test", email = "test@email.com", photo = "")
        `when`(firestoreService.saveUser(any())).thenReturn(Result.success(Unit))
        val result = userRepository.saveUser(user)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `saveUser returns failure when firestoreService returns failure`() = runBlocking {
        val user = UserModel(id = "1", name = "Test", email = "test@email.com", photo = "")
        val exception = Exception("Save failed")
        `when`(firestoreService.saveUser(any())).thenReturn(Result.failure(exception))
        val result = userRepository.saveUser(user)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getProfileData returns user when firestoreService returns user`() = runBlocking {
        val user = UserModel(id = "2", name = "User", email = "user@email.com", photo = "")
        `when`(firestoreService.getProfileData(any())).thenReturn(Result.success(user))
        val result = userRepository.getProfileData("2")
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `getProfileData returns failure when firestoreService returns failure`() = runBlocking {
        val exception = Exception("Profile fetch failed")
        `when`(firestoreService.getProfileData(any())).thenReturn(Result.failure(exception))
        val result = userRepository.getProfileData("2")
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `uploadUserImage returns success when storageService returns uri`() = runBlocking {
        val uri = mock<Uri>()
        `when`(storageService.uploadUserImage(any(), any())).thenReturn(Result.success(uri))
        val result = userRepository.uploadUserImage(uri, "1")
        assertTrue(result.isSuccess)
        assertEquals(uri, result.getOrNull())
    }

    @Test
    fun `uploadUserImage returns failure when storageService returns failure`() = runBlocking {
        val uri = mock<Uri>()
        val exception = Exception("Upload failed")
        `when`(storageService.uploadUserImage(any(), any())).thenReturn(Result.failure(exception))
        val result = userRepository.uploadUserImage(uri, "1")
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getContacts emits success result with user list when firestoreService returns users`() =
        runBlocking {
            val users = listOf(
                UserModel(id = "1", name = "A", email = "a@email.com", photo = ""),
                UserModel(id = "2", name = "B", email = "b@email.com", photo = "")
            )
            `when`(firestoreService.getContacts(any())).thenReturn(flowOf(Result.success(users)))
            val flow = userRepository.getContacts("1")
            flow.collect { res ->
                assertTrue(res.isSuccess)
                assertEquals(users, res.getOrNull())
            }
        }

    @Test
    fun `getContacts emits failure result when firestoreService returns failure`() = runBlocking {
        val exception = Exception("Contacts fetch failed")
        `when`(firestoreService.getContacts(any())).thenReturn(flowOf(Result.failure(exception)))
        val flow = userRepository.getContacts("1")
        flow.collect { res ->
            assertTrue(res.isFailure)
            assertEquals(exception, res.exceptionOrNull())
        }
    }

    @Test
    fun `updateProfile returns success when firestoreService returns success`() {
        `when`(firestoreService.updateProfile(any(), any())).thenReturn(Result.success(Unit))
        val result = userRepository.updateProfile("1", mapOf("name" to "New Name"))
        assertTrue(result.isSuccess)
    }

    @Test
    fun `updateProfile returns failure when firestoreService returns failure`() {
        val exception = Exception("Update failed")
        `when`(firestoreService.updateProfile(any(), any())).thenReturn(Result.failure(exception))
        val result = userRepository.updateProfile("1", mapOf("name" to "New Name"))
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}