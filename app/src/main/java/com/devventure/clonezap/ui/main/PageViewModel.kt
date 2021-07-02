package com.devventure.clonezap.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.repository.UserRepository

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

//    private val _contactsList = MutableLiveData<ArrayList<Contact>>().apply {
//        UserRepository.getMyContacts {
//            value = it
//        }
//    }
//
//    val contactsList: LiveData<ArrayList<Contact>> = _contactsList

    fun setIndex(index: Int) {
        _index.value = index
    }
}