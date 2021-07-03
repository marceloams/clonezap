package com.devventure.clonezap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.devventure.clonezap.databinding.ActivityAddContactBinding
import com.devventure.clonezap.databinding.ActivityChatBinding
import com.devventure.clonezap.model.User
import com.devventure.clonezap.repository.UserRepository

class AddContact : AppCompatActivity() {
    lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.inputContactName.text.toString()
            val email = binding.inputContactEmail.text.toString()

            if(name != "" && email != ""){
                val user: User = User(name, email)
                UserRepository.addUserToMyContacts(user)
                finish()
            } else {
                Toast.makeText(this, "Name and Email can't be empty! ", Toast.LENGTH_LONG).show()
            }
        }
    }
}