package com.deiovannagroup.whatsapp_concept.utils

import androidx.recyclerview.widget.DiffUtil
import com.deiovannagroup.whatsapp_concept.models.ChatModel


object ChatDiffCallback : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return oldItem.lastMessage == newItem.lastMessage
    }

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return oldItem == newItem
    }
}