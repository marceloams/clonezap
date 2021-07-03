package com.devventure.clonezap.ui.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devventure.clonezap.model.Message
import com.devventure.clonezap.repository.ChatRepository

class ChatViewModel : ViewModel() {
    var chatId: String = ""

    private val _messagesList = MutableLiveData<ArrayList<Message>>().apply {
        ChatRepository.getMessages(chatId) {
            value = it
        }
    }

    val messagesList: LiveData<ArrayList<Message>> = _messagesList
}