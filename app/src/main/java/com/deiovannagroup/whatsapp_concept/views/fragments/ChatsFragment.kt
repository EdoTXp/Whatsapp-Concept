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
import com.deiovannagroup.whatsapp_concept.databinding.FragmentChatsBinding
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.viewmodels.ChatsViewModel
import com.deiovannagroup.whatsapp_concept.views.activities.MessagesActivity
import com.deiovannagroup.whatsapp_concept.views.adapters.ChatsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatsFragment : Fragment() {
    private lateinit var binding: FragmentChatsBinding
    private lateinit var chatsAdapter: ChatsAdapter

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

        chatsAdapter = ChatsAdapter { chat ->
            val intent = Intent(
                context,
                MessagesActivity::class.java,
            )

            val user = UserModel(
                id = chat.userIdReceived,
                name = chat.name,
                photo = chat.photo,
            )

            intent.putExtra("remitted", user)
            startActivity(intent)


        }

        binding.rvChats.adapter = chatsAdapter
        binding.rvChats.layoutManager = LinearLayoutManager(context)
        binding.rvChats.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
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
                    if (chats.isNotEmpty())
                        chatsAdapter.submitList(chats)
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