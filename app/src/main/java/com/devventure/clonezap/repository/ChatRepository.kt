package com.devventure.clonezap.repository

import android.util.Log
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.DiskLruCache
import java.util.*
import kotlin.collections.ArrayList

object ChatRepository {
    private const val TAG: String = "ChatRepository"
    private val db by lazy { Firebase.firestore}
    private val currentUserEmail by lazy { UserRepository.myEmail() }

    fun getMessages(chatId: String, onComplete: (ArrayList<Message>) -> Unit){
        val docRef = db.collection("chats")
            .document(chatId)
            .collection("messages").addSnapshotListener{ snapshot, e ->
                if(e != null){
                    onComplete(ArrayList<Message>())
                    Log.d(TAG, e.localizedMessage)
                    return@addSnapshotListener
                }

                val messages = ArrayList<Message>()
                if (snapshot != null) {
                    for(msg in snapshot){
                        val m = msg.toObject<Message>()
                        messages.add(m)
                    }
                    messages.sortBy { it.time }
                    onComplete(messages)
                } else {
                    Log.d(TAG, "snapshot is null")
                }
            }
    }

    fun createChatId(email1: String, email2: String): String {
        return if(email1.compareTo(email2) > 0) "$email1-${email2}" else "${email2}-$email1"
    }

    fun getChatWith(contactEmail: String, onComplete: (chatId: String, e: String?) -> Unit) {
        val chat = createChatId(currentUserEmail!!, contactEmail)
        db.collection("chats")
            .document(chat)
            .get()
            .addOnSuccessListener {
                onComplete(it.id, null)
            }
            .addOnFailureListener {
                onComplete("", it.localizedMessage)
            }
    }

    fun addMessageToChat(chatId: String, from: String, message: String){
        val data = hashMapOf(
            "from" to from,
            "message" to message,
            "time" to Date().time
        )

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .document()
            .set(data)
    }

//    fun getMessages(myEmail: String, contactEmail: String, onComplete: (ArrayList<String>) -> Unit){
//        val docRef = ChatRepository.db.collection("chats")
//            .whereArrayContains("users", listOf(myEmail, contactEmail))
//            .get().addOnSuccessListener { documents ->
//                for(doc in documents){
//                    doc.reference.collection("chats").addSnapshotListener{ snapshot, e ->
//                        if(e != null){
//                            onComplete(ArrayList<String>())
//                            Log.d(TAG, e.localizedMessage)
//                            return@addSnapshotListener
//                        }
//
//                        val messages = ArrayList<String>()
//                        if (snapshot != null) {
//                            for(msg in snapshot){
//                                messages.add(msg.data.getValue("message") as String)
//                                Log.d(TAG, msg.data.getValue("message") as String)
//                            }
//                            onComplete(messages)
//                        } else {
//                            Log.d(TAG, "snapshot is null")
//                        }
//                        return@addSnapshotListener
//                    }
//                    break
//                }
//            }
//
//    }
}