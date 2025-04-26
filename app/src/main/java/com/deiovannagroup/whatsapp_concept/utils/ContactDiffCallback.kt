package com.deiovannagroup.whatsapp_concept.utils

import androidx.recyclerview.widget.DiffUtil
import com.deiovannagroup.whatsapp_concept.models.UserModel

object ContactDiffCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }
}