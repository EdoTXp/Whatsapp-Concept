package com.deiovannagroup.whatsapp_concept.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Data model representing a single message in a chat.
 *
 * @property idUser The unique identifier of the user who sent the message.
 * @property message The content of the message.
 * @property date The timestamp when the message was sent, automatically set by Firestore server.
 */
data class MessageModel(
    val idUser: String = "",
    val message: String = "",
    @ServerTimestamp
    val date: Date? = null,
)
