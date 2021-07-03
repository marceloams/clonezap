package com.devventure.clonezap.ui.main.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.devventure.clonezap.databinding.FragmentMainBinding
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.repository.ChatRepository
import com.devventure.clonezap.ChatActivity

/**
 * A placeholder fragment containing a simple view.
 */
class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val contactsList: RecyclerView = binding.contactsList
        val adapter: ContactsAdapter = ContactsAdapter{ contact ->
            onContactSelected(contact)
        }
        contactsViewModel.contactsList.observe(viewLifecycleOwner, {
            adapter.setContactsList(it)
        })

        contactsList.adapter = adapter

        return root
    }

    private fun onContactSelected(contact: Contact) {
        ChatRepository.getChatWith(contact.email) { chatId, e ->
            if (e != null) {
                Toast.makeText(context, e, Toast.LENGTH_LONG).show()
            } else {
                goToChat(chatId, contact.name)
            }
        }
    }

    private fun goToChat(chatId: String, contactName: String) {
        val intent: Intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("contactName", contactName)
        startActivity(intent)
    }

//    private fun onContactsSelected(contact: Contact){
//        val intent: Intent = Intent(context, ChatActivity::class.java)
//
//        val email = UserRepository.myEmail()
//        val chatId = ChatRepository.createChatId(email!!, contact.email)
//
//        intent.putExtra("chatId", chatId)
//        intent.putExtra("contactEmail", contact.email)
//        startActivity(intent)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}