package com.deiovannagroup.whatsapp_concept.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.deiovannagroup.whatsapp_concept.databinding.FragmentContactsBinding
import com.deiovannagroup.whatsapp_concept.viewmodels.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding

    private val contactsViewModel by lazy {
        ViewModelProvider(this)[ContactsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(
            inflater,
            container,
            false,
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
                contacts.forEach { user ->
                    Log.i("ContactsFragment", "Contacts: ${user.name}")
                }
            }
            result.onFailure {
                Log.e("ContactsFragment", "Error: ${it.message}")
            }
        }
    }

}