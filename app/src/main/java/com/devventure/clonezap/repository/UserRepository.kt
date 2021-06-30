package com.devventure.clonezap.repository

import com.devventure.clonezap.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserRepository {

    val db by lazy {Firebase.firestore}

    fun addUser(user: User, onSuccess: () -> Unit, onFail: (error: String) -> Unit){
        // Add a new document with a generated ID
        db.collection("users")
            .document(user.email)
            .set(user)
            .addOnSuccessListener { documentReference ->
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFail(e.localizedMessage)
            }
    }
}