package com.devventure.clonezap.ui.main

import android.content.Intent
import androidx.lifecycle.*
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.model.User
import com.devventure.clonezap.repository.UserRepository

/**
 * A placeholder fragment containing a simple view.
 */
class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<ArrayList<Contact>>().apply {
        UserRepository.getMyContacts {
            value = it
        }
    }

    val contactsList: LiveData<ArrayList<Contact>> = _contactsList
}