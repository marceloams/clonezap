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
    const val TAG: String = "ChatRepository"
    val db by lazy { Firebase.firestore}

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

    fun getMessages(chatId: String, onComplete: (ArrayList<Message>) -> Unit){
        val docRef = db.collection("chats")
            .document(chatId)
            .collection("messages").addSnapshotListener{ snapshot, e ->
                Log.d(TAG, "encontrei")
                if(e != null){
                    onComplete(ArrayList<Message>())
//                    Log.d(TAG, e.localizedMessage)
                    Log.d(TAG, "deu ruim")
                    return@addSnapshotListener
                }

                val messages = ArrayList<Message>()
                if (snapshot != null) {
                    Log.d(TAG, "size: ${snapshot.size()}")
                    for(msg in snapshot){
                        val m = msg.toObject<Message>()
                        messages.add(m)
                    }
                    messages.sortBy { it.time }
                    onComplete(messages)
                } else {
                    Log.d(TAG, "snapshot is null")
                }
                //return@addSnapshotListener
            }
    }

    fun createChatId(email: String, email2: String): String {
        val id = if(email.compareTo(email2) > 0) "$email-${email2}" else "${email2}-$email"
        return id
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
}