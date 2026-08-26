package com.example.conversationalai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val USER = 0
        private const val BOT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) USER else BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == USER) {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_message, parent, false)

            UserViewHolder(view)

        } else {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bot_message, parent, false)

            BotViewHolder(view)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message = messages[position]

        if (holder is UserViewHolder) {
            holder.message.text = message.message
        }

        if (holder is BotViewHolder) {
            holder.message.text = message.message
        }
    }

    fun addMessage(chatMessage: ChatMessage) {
        messages.add(chatMessage)
        notifyItemInserted(messages.size - 1)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.txtUserMessage)
    }

    class BotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.txtBotMessage)
    }
}