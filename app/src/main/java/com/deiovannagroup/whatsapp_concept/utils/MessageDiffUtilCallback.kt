package com.deiovannagroup.whatsapp_concept.utils

import androidx.recyclerview.widget.DiffUtil
import com.deiovannagroup.whatsapp_concept.models.MessageModel

object MessageDiffUtilCallback : DiffUtil.ItemCallback<MessageModel>() {
    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.idUser == newItem.idUser
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem == newItem
    }
}