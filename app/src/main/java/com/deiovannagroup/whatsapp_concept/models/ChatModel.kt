package com.deiovannagroup.whatsapp_concept.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ChatModel(
    val userIdRemitted: String = "",
    val userIdReceived: String = "",
    val photo: String = "",
    val name: String = "",
    val lastMessage: String = "",
    @ServerTimestamp
    val date: Date? = null,
)
