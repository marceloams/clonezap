package com.devventure.clonezap.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.devventure.clonezap.ui.main.login.LoginActivity
import com.devventure.clonezap.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel (val context: Context) {
    fun loadView(){

        val user = FirebaseAuth.getInstance().currentUser

        val intent: Intent = if(user == null){
            Intent(context, LoginActivity::class.java)
        } else{
            Intent(context, MainActivity::class.java)
        }

        context.startActivity(intent)
        (context as Activity).finish()
    }
}