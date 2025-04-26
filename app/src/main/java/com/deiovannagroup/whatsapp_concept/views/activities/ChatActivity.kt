package com.deiovannagroup.whatsapp_concept.views.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.deiovannagroup.whatsapp_concept.databinding.ActivityChatBinding
import com.deiovannagroup.whatsapp_concept.models.UserModel
import com.deiovannagroup.whatsapp_concept.utils.Constants

class ChatActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }

    private var dataRemitted: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        getUserDataRemitted()
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