package com.deiovannagroup.whatsapp_concept.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.deiovannagroup.whatsapp_concept.databinding.ItemMessageReceivedBinding
import com.deiovannagroup.whatsapp_concept.databinding.ItemMessageRemittedBinding
import com.deiovannagroup.whatsapp_concept.models.MessageModel
import com.deiovannagroup.whatsapp_concept.utils.MessageDiffUtilCallback
import com.deiovannagroup.whatsapp_concept.utils.Constants


class MessageAdapter(private val userId: String) :
    ListAdapter<MessageModel, ViewHolder>(MessageDiffUtilCallback) {

    class MessageRemittedViewHolder(private val binding: ItemMessageRemittedBinding) :
        ViewHolder(binding.root) {

        fun bind(message: MessageModel) {
            binding.textMessageRemitted.text = message.message
        }

        companion object {
            fun inflate(parent: ViewGroup): MessageRemittedViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemMessageRemittedBinding.inflate(
                    inflater,
                    parent,
                    false,
                )

                return MessageRemittedViewHolder(itemView)
            }
        }
    }

    class MessageReceivedViewHolder(private val binding: ItemMessageReceivedBinding) :
        ViewHolder(binding.root) {

        fun bind(message: MessageModel) {
            binding.textMessageReceived.text = message.message
        }

        companion object {
            fun inflate(parent: ViewGroup): MessageReceivedViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = ItemMessageReceivedBinding.inflate(
                    inflater,
                    parent,
                    false,
                )
                return MessageReceivedViewHolder(itemView)
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
            return MessageRemittedViewHolder.inflate(parent)
        }

        return MessageReceivedViewHolder.inflate(parent)

    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val message = getItem(position)
        when (holder) {
            is MessageRemittedViewHolder -> holder.bind(message)
            is MessageReceivedViewHolder -> holder.bind(message)
        }
    }


}