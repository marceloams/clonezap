package com.devventure.clonezap.ui.main.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devventure.clonezap.R
import com.devventure.clonezap.model.Message
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var messages: ArrayList<Message> = ArrayList<Message>()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText: TextView = view.findViewById(R.id.text_chat_friend)
        private val messageTime: TextView = view.findViewById(R.id.text_hour_friend)
        private val locale = Locale("pt", "BR")
        fun setMessage(message: Message){
            messageText.text = message.message
            messageTime.text = SimpleDateFormat("HH:mm", locale).format(message.time)
        }
    }

    fun setMessagesList(messages: ArrayList<Message>){
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}