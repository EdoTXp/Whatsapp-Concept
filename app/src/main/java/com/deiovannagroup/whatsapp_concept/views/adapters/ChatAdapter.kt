package com.deiovannagroup.whatsapp_concept.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.deiovannagroup.whatsapp_concept.databinding.ItemChatReceivedBinding
import com.deiovannagroup.whatsapp_concept.databinding.ItemChatRemittedBinding
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.utils.ChatDiffUtilCallback
import com.deiovannagroup.whatsapp_concept.utils.Constants


class ChatAdapter(private val userId: String) :
    ListAdapter<ChatModel, ViewHolder>(ChatDiffUtilCallback) {

    class ChatRemittedViewHolder(private val binding: ItemChatRemittedBinding) :
        ViewHolder(binding.root) {

        fun bind(message: ChatModel) {
            binding.textChatRemitted.text = message.message
        }

        companion object {
            fun inflate(parent: ViewGroup): ChatRemittedViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemChatRemittedBinding.inflate(
                    inflater,
                    parent,
                    false,
                )

                return ChatRemittedViewHolder(itemView)
            }
        }
    }

    class ChatReceivedViewHolder(private val binding: ItemChatReceivedBinding) :
        ViewHolder(binding.root) {

        fun bind(message: ChatModel) {
            binding.textChatReceived.text = message.message
        }

        companion object {
            fun inflate(parent: ViewGroup): ChatReceivedViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemChatReceivedBinding.inflate(
                    inflater,
                    parent,
                    false,
                )
                return ChatReceivedViewHolder(itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)

        return if (userId == message.idUser) {
            Constants.REMITTER_TYPE
        } else {
            Constants.RECEIVED_TYPE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        if (viewType == Constants.REMITTER_TYPE) {
            return ChatRemittedViewHolder.inflate(parent)
        }

        return ChatReceivedViewHolder.inflate(parent)

    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val message = getItem(position)
        when (holder) {
            is ChatRemittedViewHolder -> holder.bind(message)
            is ChatReceivedViewHolder -> holder.bind(message)
        }
    }


}