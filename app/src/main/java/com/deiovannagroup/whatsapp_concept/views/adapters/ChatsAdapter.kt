package com.deiovannagroup.whatsapp_concept.views.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ListAdapter
import com.deiovannagroup.whatsapp_concept.databinding.ItemChatBinding
import com.deiovannagroup.whatsapp_concept.models.ChatModel
import com.deiovannagroup.whatsapp_concept.utils.ChatDiffCallback
import com.squareup.picasso.Picasso

class ChatsAdapter(
    private val onClick: (ChatModel) -> Unit
) : ListAdapter<
        ChatModel,
        ChatsAdapter.ChatsViewHolder,
        >(ChatDiffCallback) {

    inner class ChatsViewHolder(
        private val binding: ItemChatBinding
    ) : ViewHolder(binding.root) {

        fun bind(chat: ChatModel) {
            binding.textChatName.text = chat.name
            binding.textChatMessage.text = chat.lastMessage

            Picasso.get()
                .load(chat.photo)
                .into(binding.imageChatPhoto)

            binding.clItemChat.setOnClickListener { onClick(chat) }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemChatBinding.inflate(
            inflater,
            parent,
            false,
        )

        return ChatsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ChatsViewHolder,
        position: Int
    ) {
        val contact = getItem(position)
        holder.bind(contact)
    }


}