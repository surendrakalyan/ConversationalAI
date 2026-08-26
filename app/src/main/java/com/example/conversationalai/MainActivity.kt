package com.example.conversationalai

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton

    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Initialize Views
        rvChat = findViewById(R.id.rvChat)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)

        // Setup RecyclerView
        adapter = ChatAdapter(messageList)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = adapter

        // Welcome Message
        adapter.addMessage(
            ChatMessage(
                "👋 Hello! I am your AI Assistant.\nHow can I help you today?",
                false
            )
        )

        rvChat.scrollToPosition(adapter.itemCount - 1)

        btnSend.setOnClickListener {

            val message = etMessage.text.toString().trim()

            if (message.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a message",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Show User Message
            adapter.addMessage(
                ChatMessage(message, true)
            )

            rvChat.scrollToPosition(adapter.itemCount - 1)

            etMessage.text.clear()

            // Send to Flask Backend
            sendMessage(message)
        }
    }

    private fun sendMessage(message: String) {

        lifecycleScope.launch {

            try {

                val response = RetrofitClient.api.sendMessage(
                    ChatRequest(message)
                )

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {

                        adapter.addMessage(
                            ChatMessage(body.reply, false)
                        )

                    } else {

                        adapter.addMessage(
                            ChatMessage(
                                "Empty response from server.",
                                false
                            )
                        )

                    }

                } else {

                    adapter.addMessage(
                        ChatMessage(
                            "Server Error : ${response.code()}",
                            false
                        )
                    )

                }

            } catch (e: Exception) {

                adapter.addMessage(
                    ChatMessage(
                        "Connection Failed\n${e.localizedMessage}",
                        false
                    )
                )

            }

            rvChat.scrollToPosition(adapter.itemCount - 1)
        }
    }
}