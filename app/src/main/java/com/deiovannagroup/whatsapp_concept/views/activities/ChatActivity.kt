package com.deiovannagroup.whatsapp_concept.views.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.viewmodels.ChatViewModel
import com.deiovannagroup.whatsapp_concept.databinding.ActivityChatBinding
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.utils.Constants
import com.deiovannagroup.whatsapp_concept.utils.showMessage
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }

    private val chatViewModel by lazy {
        ViewModelProvider(this)[ChatViewModel::class.java]
    }

    private var dataRemitted: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        getUserDataRemitted()
        initToolbar()
        initListeners()
        addObservers()
        chatViewModel.getChats(dataRemitted!!.id)
    }

    override fun onStop() {
        chatViewModel.clearObserver()
        chatViewModel.chatsResult.removeObservers(this)
        chatViewModel.messageResult.removeObservers(this)
        super.onStop()
    }

    private fun addObservers() {
        chatViewModel.chatsResult.observe(this) { result ->
            result.onSuccess { chats ->
                if (chats.isNotEmpty()) {
                    chats.forEach { chat ->
                        Log.i("chat_result", "User: ${chat.idUser}, Message: ${chat.message}")
                    }
                }

            }
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_get_chats)}:" +
                            " ${error.message}"
                )
            }
        }

        chatViewModel.messageResult.observe(this) { result ->
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_send_message)}:" +
                            " ${error.message}"
                )

            }
        }
    }

    private fun initListeners() {
        binding.fabSendChat.setOnClickListener {
            val message = binding.editChats.text.toString()

            if (message.isNotEmpty()) {
                chatViewModel.sendMessage(
                    message,
                    dataRemitted!!.id,
                )

                binding.editChats.text?.clear()
            }
        }
    }

    private fun initToolbar() {
        val toolbar = binding.tbChat
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""

            if (dataRemitted == null)
                return

            binding.textName.text = dataRemitted!!.name
            Picasso.get()
                .load(dataRemitted!!.photo)
                .into(binding.imagePhotoProfile)


            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getUserDataRemitted() {
        val extras = intent.extras

        if (extras == null)
            return

        val origin = extras.getString("origin")

        if (origin == Constants.CONTACT_ORIGIN) {
            dataRemitted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getParcelable(
                    "remitted",
                    UserModel::class.java,
                )
            } else {
                @Suppress("DEPRECATION")
                extras.getParcelable("remitted")
            }
        } else if (origin == Constants.CHAT_ORIGIN) {

        }

    }

    private fun setEdgeToEdgeLayout() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
    }
}