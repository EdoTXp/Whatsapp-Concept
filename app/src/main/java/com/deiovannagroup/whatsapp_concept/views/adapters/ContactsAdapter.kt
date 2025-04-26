package com.deiovannagroup.whatsapp_concept.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.deiovannagroup.whatsapp_concept.databinding.ItemContactBinding
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.squareup.picasso.Picasso
import com.deiovannagroup.whatsapp_concept.utils.ContactDiffCallback

class ContactsAdapter : ListAdapter<
        UserModel,
        ContactsAdapter.ContactsViewHolder,
        >(
    ContactDiffCallback
) {

    inner class ContactsViewHolder(
        private val binding: ItemContactBinding
    ) : ViewHolder(binding.root) {

        fun bind(contact: UserModel) {
            binding.textContactName.text = contact.name

            Picasso.get()
                .load(contact.photo)
                .into(binding.imageContactPhoto)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemContactBinding.inflate(
            inflater,
            parent,
            false,
        )

        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ContactsViewHolder,
        position: Int
    ) {
        val contact = getItem(position)
        holder.bind(contact)
    }
}