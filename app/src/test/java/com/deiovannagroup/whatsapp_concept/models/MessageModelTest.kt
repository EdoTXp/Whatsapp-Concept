package com.deiovannagroup.whatsapp_concept.models

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class MessageModelTest {

    @Test
    fun `MessageModel default values are empty or null`() {
        val messageModel = MessageModel()
        assertEquals("", messageModel.idUser)
        assertEquals("", messageModel.message)
        assertEquals(null, messageModel.date)
    }

    @Test
    fun `MessageModel assigns custom values correctly`() {
        val idUser = "user123"
        val message = "Hello, world!"
        val date = Date()
        val messageModel = MessageModel(idUser = idUser, message = message, date = date)
        assertEquals(idUser, messageModel.idUser)
        assertEquals(message, messageModel.message)
        assertEquals(date, messageModel.date)
    }

    @Test
    fun `MessageModel allows empty message with valid user`() {
        val idUser = "user456"
        val messageModel = MessageModel(idUser = idUser, message = "")
        assertEquals(idUser, messageModel.idUser)
        assertEquals("", messageModel.message)
    }

    @Test
    fun `MessageModel allows null date`() {
        val messageModel = MessageModel(idUser = "user789", message = "Test", date = null)
        assertEquals(null, messageModel.date)
    }
}