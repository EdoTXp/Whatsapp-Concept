package com.deiovannagroup.whatsapp_concept.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageModel(
    val idUser: String = "",
    val message: String = "",
    @ServerTimestamp
    val date: Date? = null,
)
