package com.deiovannagroup.whatsapp_concept.repositories

import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.services.FirebaseFirestoreService
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.util.Date

class MessageRepositoryTest {

    private lateinit var firestore: FirebaseFirestoreService
    private lateinit var messageRepository: MessageRepository

    @Before
    fun setUp() {
        firestore = mock()
        messageRepository = MessageRepository(firestore)
    }

    @Test
    fun `getMessages emits success result with message list when firestore returns messages`() =
        runBlocking {
            val messages = listOf(
                MessageModel(idUser = "user1", message = "Hi", date = Date(1234567890)),
                MessageModel(idUser = "user2", message = "Hello", date = Date(1234567891))
            )
            `when`(firestore.getMessages(any(), any())).thenReturn(flowOf(Result.success(messages)))
            val flow = messageRepository.getMessages("user1", "user2")
            flow.collect { res ->
                assertTrue(res.isSuccess)
                assertEquals(messages, res.getOrNull())
            }
        }

    @Test
    fun `getMessages emits failure result when firestore returns failure`() = runBlocking {
        val exception = Exception("Fetch failed")
        `when`(firestore.getMessages(any(), any())).thenReturn(flowOf(Result.failure(exception)))
        val flow = messageRepository.getMessages("user1", "user2")
        flow.collect { res ->
            assertTrue(res.isFailure)
            assertEquals(exception, res.exceptionOrNull())
        }
    }
}