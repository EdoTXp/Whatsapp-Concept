package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ChatsRepositoryTest {

    private lateinit var firestore: FirebaseFirestoreService
    private lateinit var chatsRepository: ChatsRepository

    @Before
    fun setUp() {
        firestore = mock()
        chatsRepository = ChatsRepository(firestore)
    }

    @Test
    fun sendChatReturnsSuccessWhenFirestoreSucceeds() = runTest {
        val chat =
            ChatModel(userIdRemitted = "user1", userIdReceived = "user2", lastMessage = "Hello")
        `when`(firestore.sendChat(any())).thenReturn(Result.success(Unit))
        val result = chatsRepository.sendChat(chat)
        assertTrue(result.isSuccess)
    }

    @Test
    fun sendChatReturnsFailureWhenFirestoreFails() = runTest {
        val chat =
            ChatModel(userIdRemitted = "user1", userIdReceived = "user2", lastMessage = "Fail")
        val exception = Exception("Firestore error")
        `when`(firestore.sendChat(any())).thenReturn(Result.failure(exception))
        val result = chatsRepository.sendChat(chat)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun getChatsEmitsSuccessResultWithChatList() = runTest {
        val userId = "user1"
        val chatList = listOf(
            ChatModel(
                userIdRemitted = "user1", userIdReceived = "user2", lastMessage = "Hi"
            )
        )
        `when`(firestore.getChats(userId)).thenReturn(flowOf(Result.success(chatList)))
        val result = chatsRepository.getChats(userId).first()
        assertTrue(result.isSuccess)
        assertEquals(chatList, result.getOrNull())
    }

    @Test
    fun getChatsEmitsFailureResultWhenFirestoreFails() = runTest {
        val userId = "user2"
        val exception = Exception("Fetch error")
        `when`(firestore.getChats(userId)).thenReturn(flowOf(Result.failure(exception)))
        val result = chatsRepository.getChats(userId).first()
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun getChatsEmitsEmptyListWhenNoChatsAvailable() = runTest {
        val userId = "user3"
        `when`(firestore.getChats(userId)).thenReturn(flowOf(Result.success(emptyList())))
        val result = chatsRepository.getChats(userId).first()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}