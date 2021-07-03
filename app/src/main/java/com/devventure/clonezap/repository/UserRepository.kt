package com.devventure.clonezap.repository

import android.util.Log
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

object UserRepository {
    private const val TAG: String = "UserRepository"
    private val db by lazy {Firebase.firestore}

    fun myEmail(): String? {
        return  FirebaseAuth.getInstance().currentUser?.email ?: ""
    }

    fun addUser(user: User, onSuccess: () -> Unit, onFail: (error: String) -> Unit){
        // Add a new document with a generated ID
        db.collection("users")
            .document(user.email)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFail(e.localizedMessage)
            }
    }

    fun getUserByEmail(email: String, onComplete: (user: User, e: String?) -> Unit){
        db.collection("users")
            .document(email)
            .get().addOnSuccessListener{
                Log.d(TAG, "User found? ${it.exists()}")
            }.addOnFailureListener{
                onComplete(User(), it.localizedMessage)
            }
    }

    fun addUserToMyContacts(user: User){
        val data = hashMapOf(
            "email" to user.email,
            "name" to user.email,
        )

        db.collection("users")
            .document(this.myEmail().toString())
            .collection("contacts")
            .document(user.email)
            .set(data)
    }

    fun getMyContacts(onComplete: (ArrayList<Contact>) -> Unit){
        val current = FirebaseAuth.getInstance().currentUser?.apply {
            if(this.email != null){
                val docRef = db.collection("users")
                    .document(this.email!!)
                    .collection("contacts")
                docRef.get()
                    .addOnSuccessListener { documents ->
                        if (documents != null) {
                            val contacts = ArrayList<Contact>()
                            for (document in documents) {
//                                contacts.add(document.toObject<Contact>())
                                contacts.add(Contact(
                                    name = document.data.getValue("name") as String,
                                    email = document.data.getValue("email") as String,
                                ))
                            }
                            onComplete(contacts)
                        } else {
                            Log.d(TAG, "No contacts found")
                            onComplete(ArrayList<Contact>())
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Get contacts failed with ", exception)
                        onComplete(ArrayList<Contact>())
                    }
            } else {
                Log.d(TAG, "Current user has no email")
                onComplete(ArrayList<Contact>())
            }
        }
        Log.d(TAG, "Current user not found")
        onComplete(ArrayList<Contact>())
    }
}