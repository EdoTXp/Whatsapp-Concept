package com.deiovannagroup.whatsapp_concept.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Data model representing a chat between two users.
 *
 * @property userIdRemitted The unique identifier of the user who sent the message.
 * @property userIdReceived The unique identifier of the user who received the message.
 * @property photo The URL of the user's profile photo.
 * @property name The display name of the chat or user.
 * @property lastMessage The content of the last message exchanged in the chat.
 * @property date The timestamp of the last message, automatically set by Firestore server.
 */
data class ChatModel(
    val userIdRemitted: String = "",
    val userIdReceived: String = "",
    val photo: String = "",
    val name: String = "",
    val lastMessage: String = "",
    @ServerTimestamp
    val date: Date? = null,
)
