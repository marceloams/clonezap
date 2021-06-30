package com.devventure.clonezap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.devventure.clonezap.databinding.ActivityLoginBinding
import com.devventure.clonezap.model.User
import com.devventure.clonezap.repository.UserRepository
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123
    private lateinit var binding: ActivityLoginBinding

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        activityResult(result.resultCode, result.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        binding.btnLogin.setOnClickListener {
            resultLauncher.launch(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()
            )
        }
    }

    private fun activityResult(resultCode: Int, data: Intent?) {

            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val currentUser = FirebaseAuth.getInstance().currentUser?.apply {
                    val user: User = User(
                        name = this.displayName?:"NO NAME",
                        email = this.email ?: "NO EMAIL",
                        id = this.uid
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

    fun failToLogin(message: String?){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun navigateToMain(){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
//                val intent: Intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, response?.error?.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}
