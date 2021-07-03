package com.devventure.clonezap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.devventure.clonezap.R
import com.devventure.clonezap.databinding.ActivityChatBinding
import com.devventure.clonezap.repository.ChatRepository
import com.devventure.clonezap.repository.UserRepository
import com.devventure.clonezap.ui.main.chat.ChatAdapter
import com.devventure.clonezap.ui.main.chat.ChatViewModel
import com.devventure.clonezap.ui.main.contacts.ContactsAdapter
import com.devventure.clonezap.ui.main.contacts.ContactsViewModel
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatViewModel: ChatViewModel
    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val contactName = intent.getStringExtra("contactName")
        binding.toolbar.title = contactName

        val chatId = intent.getStringExtra("chatId")
        if(chatId == null) {
            finish()
            return
        }

        val me = UserRepository.myEmail()
//        val messages = binding.txtMessages

        val messagesList: RecyclerView = binding.messagesList
        val adapter: ChatAdapter = ChatAdapter()

        ChatRepository.getMessages(chatId){
            adapter.setMessagesList(it)
        }

//        chatViewModel.messagesList.observe(this, {
//            adapter.setMessagesList(it)
//        })

        messagesList.adapter = adapter

        binding.btnSend.setOnClickListener{
            val msg = binding.txtSendMessage.text.toString()
            if(msg != null){
                ChatRepository.addMessageToChat(chatId, me!!, msg)
                binding.txtSendMessage.text.clear()
            }
        }
    }
}