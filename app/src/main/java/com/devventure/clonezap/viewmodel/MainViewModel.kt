package com.devventure.clonezap.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.devventure.clonezap.ui.main.login.LoginActivity
import com.firebase.ui.auth.AuthUI

class MainViewModel (val context: Context){
    fun logout (): Boolean{
        AuthUI.getInstance().signOut(context).addOnCompleteListener {
            val intent: Intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
        return true;
    }
}