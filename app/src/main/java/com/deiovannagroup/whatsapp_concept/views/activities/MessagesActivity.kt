package com.deiovannagroup.whatsapp_concept.views.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.viewmodels.MessagesViewModel
import com.deiovannagroup.whatsapp_concept.databinding.ActivityMessagesBinding
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.utils.showMessage
import com.deiovannagroup.whatsapp_concept.views.adapters.MessageAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMessagesBinding.inflate(layoutInflater)
    }

    private val messagesViewModel by lazy {
        ViewModelProvider(this)[MessagesViewModel::class.java]
    }

    private lateinit var messageAdapter: MessageAdapter
    private var dataUserReceived: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        getReceivedUserData()
        initToolbar()
        initListeners()
        addObservers()
        messagesViewModel.getMessages(dataUserReceived!!.id)
        initRecyclerView()
    }


    override fun onStop() {
        messagesViewModel.clearObserver()
        messagesViewModel.messagesResult.removeObservers(this)
        messagesViewModel.sendChatResult.removeObservers(this)
        messagesViewModel.sendMessageResult.removeObservers(this)
        super.onStop()
    }

    private fun initRecyclerView() {
        with(binding) {
            messageAdapter = MessageAdapter(messagesViewModel.getUserId())

            rvMessages.adapter = messageAdapter
            rvMessages.layoutManager = LinearLayoutManager(this@MessagesActivity)
        }
    }

    private fun addObservers() {
        messagesViewModel.messagesResult.observe(this) { result ->
            result.onSuccess { messages ->
                if (messages.isNotEmpty()) {
                    messageAdapter.submitList(messages)
                }

            }
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_get_messages)}:" +
                            " ${error.message}"
                )
            }
        }

        messagesViewModel.sendChatResult.observe(this) { result ->
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_get_chat)}:" +
                            " ${error.message}"
                )
            }
        }

        messagesViewModel.sendMessageResult.observe(this) { result ->
            result.onFailure { error ->
                showMessage(
                    "${getString(R.string.error_send_message)}:" +
                            " ${error.message}"
                )

            }
        }
    }

    private fun initListeners() {
        binding.fabSendMessage.setOnClickListener {
            val message = binding.editMessage.text.toString()

            if (message.isNotEmpty()) {
                messagesViewModel.sendMessage(
                    message,
                    dataUserReceived!!.id,
                )

                messagesViewModel.sendChat(
                    dataUserReceived!!.id,
                    dataUserReceived!!.photo,
                    dataUserReceived!!.name,
                    message,
                )

                binding.editMessage.text?.clear()
            }
        }
    }

    private fun initToolbar() {
        val toolbar = binding.tbMessages
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""

            if (dataUserReceived == null)
                return

            binding.textName.text = dataUserReceived!!.name
            Picasso.get()
                .load(dataUserReceived!!.photo)
                .into(binding.imagePhotoProfile)


            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getReceivedUserData() {
        // get received user data
        val extras = intent.extras

        if (extras == null)
            return
        dataUserReceived = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            extras.getParcelable(
                "remitted",
                UserModel::class.java,
            )
        } else {
            @Suppress("DEPRECATION")
            extras.getParcelable("remitted")
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