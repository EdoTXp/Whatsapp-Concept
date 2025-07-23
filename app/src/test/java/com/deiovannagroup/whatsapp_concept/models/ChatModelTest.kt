package com.deiovannagroup.whatsapp_concept.models

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class ChatModelTest {

    @Test
    fun `test ChatModel default values`() {
        // Arrange
        val chatModel = ChatModel()

        // Assert
        assertEquals("", chatModel.userIdRemitted)
        assertEquals("", chatModel.userIdReceived)
        assertEquals("", chatModel.photo)
        assertEquals("", chatModel.name)
        assertEquals("", chatModel.lastMessage)
        assertEquals(null, chatModel.date)
    }

    @Test
    fun `test ChatModel custom values`() {
        // Arrange
        val userIdRemitted = "user1"
        val userIdReceived = "user2"
        val photo = "https://example.com/photo.jpg"
        val name = "Chat Name"
        val lastMessage = "Hello!"
        val date = Date()

        val chatModel = ChatModel(
            userIdRemitted = userIdRemitted,
            userIdReceived = userIdReceived,
            photo = photo,
            name = name,
            lastMessage = lastMessage,
            date = date
        )

        // Assert
        assertEquals(userIdRemitted, chatModel.userIdRemitted)
        assertEquals(userIdReceived, chatModel.userIdReceived)
        assertEquals(photo, chatModel.photo)
        assertEquals(name, chatModel.name)
        assertEquals(lastMessage, chatModel.lastMessage)
        assertEquals(date, chatModel.date)
    }
}