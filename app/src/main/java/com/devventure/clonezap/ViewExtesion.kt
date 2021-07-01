package com.devventure.clonezap

import android.view.View
import com.google.firebase.auth.FirebaseAuth

fun View.isLogged(): Boolean{
    val user = FirebaseAuth.getInstance().currentUser
    return user != null
}