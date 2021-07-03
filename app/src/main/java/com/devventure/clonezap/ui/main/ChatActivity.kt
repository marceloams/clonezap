package com.devventure.clonezap.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devventure.clonezap.R
import com.devventure.clonezap.databinding.ActivityChatBinding
import com.devventure.clonezap.repository.ChatRepository
import com.devventure.clonezap.repository.UserRepository
import java.util.*

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatId = intent.getStringExtra("chatId")
        val contactEmail = intent.getStringExtra("contactEmail")
        if(chatId == null || contactEmail == null){
            finish()
            return
        }

        val me = UserRepository.myEmail()
        val messages = binding.txtMessages

        ChatRepository.getMessages(chatId){
            messages.text.clear()
            for(msg in it){
                messages.text.append("${msg.message}\n")
            }
        }

        binding.btnSend.setOnClickListener{
            val msg = binding.txtSendMessage.text.toString()
            if(msg != null){
                ChatRepository.addMessageToChat(chatId, me!!, msg)
                binding.txtSendMessage.text.clear()
            }
        }


    }
}