package com.deiovannagroup.whatsapp_concept.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.FragmentChatsBinding
import com.deiovannagroup.whatsapp_concept.viewmodels.ChatsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatsFragment : Fragment() {
    private lateinit var binding: FragmentChatsBinding

    private val chatsViewModel by lazy {
        ViewModelProvider(this)[ChatsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(
            inflater,
            container,
            false,
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        addObservers()
        chatsViewModel.getChats()
    }

    override fun onDestroy() {
        chatsViewModel.clearObserver()
        chatsViewModel.chatsResult.removeObservers(this)
        super.onDestroy()
    }

    private fun addObservers() {
        chatsViewModel.chatsResult.observe(this) { result ->
            result.onSuccess { chats ->
                chats.forEach { chat ->
                    if (chats.isNotEmpty()) {
                        Log.i(
                            "show_chat",
                            "${chat.name} - ${chat.lastMessage}",
                        )
                    }
                }

            }
            result.onFailure { error ->
                Toast.makeText(
                    requireContext(),
                    "${getString(R.string.error_get_chat)} : ${error.message}",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }

}