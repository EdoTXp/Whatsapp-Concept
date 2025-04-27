package com.deiovannagroup.whatsapp_concept.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.FragmentContactsBinding
import com.deiovannagroup.whatsapp_concept.viewmodels.ContactsViewModel
import com.deiovannagroup.whatsapp_concept.views.activities.MessagesActivity
import com.deiovannagroup.whatsapp_concept.views.adapters.ContactsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactsAdapter: ContactsAdapter

    private val contactsViewModel by lazy {
        ViewModelProvider(this)[ContactsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(
            inflater,
            container,
            false,
        )

        contactsAdapter = ContactsAdapter { contact ->
            val intent = Intent(
                context,
                MessagesActivity::class.java,
            )

            intent.putExtra("remitted", contact)
            startActivity(intent)
        }
        binding.rvContacts.adapter = contactsAdapter

        binding.rvContacts.layoutManager = LinearLayoutManager(context)
        binding.rvContacts.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL,
            ),
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        addObservers()
        contactsViewModel.getContacts()
    }

    override fun onDestroy() {
        contactsViewModel.clearObserver()
        contactsViewModel.contactsResult.removeObservers(this)
        super.onDestroy()
    }

    private fun addObservers() {
        contactsViewModel.contactsResult.observe(this) { result ->
            result.onSuccess { contacts ->
                if (contacts.isNotEmpty()) {
                    contactsAdapter.submitList(contacts)
                }
            }
            result.onFailure { error ->
                Toast.makeText(
                    requireContext(),
                    "${getString(R.string.error_getting_contacts)} : ${error.message}",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

}