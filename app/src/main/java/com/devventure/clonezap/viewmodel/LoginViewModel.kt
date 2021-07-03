package com.devventure.clonezap.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.devventure.clonezap.MainActivity
import com.devventure.clonezap.model.User
import com.devventure.clonezap.repository.UserRepository
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel (val context: Context) {

    fun activityResult(resultCode: Int, data: Intent?) {

        val response = IdpResponse.fromResultIntent(data)

        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            val currentUser = FirebaseAuth.getInstance().currentUser?.apply {
                val user: User = User(
                    name = this.displayName?:"NO NAME",
                    email = this.email ?: "NO EMAIL"
//                    id = this.uid
                )
                UserRepository.addUser(user, {
                    navigateToMain()
                }, {
                    failToLogin(it)
                })
            }
        } else {
            failToLogin((response?.error?.message))
        }

    }

    private fun failToLogin(message: String?){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToMain(){
        val intent: Intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

}