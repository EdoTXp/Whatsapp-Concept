package com.deiovannagroup.whatsapp_concept.models

import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelTest {

    @Test
    fun `UserModel default values are empty strings`() {
        val user = UserModel()
        assertEquals("", user.id)
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertEquals("", user.photo)
    }

    @Test
    fun `UserModel assigns custom values correctly`() {
        val user = UserModel(
            id = "userId123",
            name = "John Doe",
            email = "john.doe@email.com",
            photo = "https://example.com/photo.jpg"
        )
        assertEquals("userId123", user.id)
        assertEquals("John Doe", user.name)
        assertEquals("john.doe@email.com", user.email)
        assertEquals("https://example.com/photo.jpg", user.photo)
    }

    @Test
    fun `UserModel allows empty name and email with valid id`() {
        val user = UserModel(id = "userId456", name = "", email = "", photo = "")
        assertEquals("userId456", user.id)
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertEquals("", user.photo)
    }

    @Test
    fun `UserModel allows empty photo url`() {
        val user = UserModel(id = "userId789", name = "Jane", email = "jane@email.com", photo = "")
        assertEquals("", user.photo)
    }
}