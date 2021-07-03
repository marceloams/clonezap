package com.devventure.clonezap.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.devventure.clonezap.databinding.FragmentMainBinding
import com.devventure.clonezap.model.Contact
import com.devventure.clonezap.repository.ChatRepository
import com.devventure.clonezap.repository.UserRepository

/**
 * A placeholder fragment containing a simple view.
 */
class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java).apply {
//            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val contactsList: RecyclerView = binding.contactsList
        val adapter: ContactsAdapter = ContactsAdapter({ contact ->
            onContactsSelected(contact)
        })
        contactsViewModel.contactsList.observe(viewLifecycleOwner, {
            adapter.setContactsList(it)
        })

        contactsList.adapter = adapter

        return root
    }

    private fun onContactsSelected(contact: Contact){
        val intent: Intent = Intent(context, ChatActivity::class.java)

        val email = UserRepository.myEmail()
        val chatId = ChatRepository.createChatId(email!!, contact.email)

        intent.putExtra("chatId", chatId)
        intent.putExtra("contactEmail", contact.email)
        startActivity(intent)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ContactsFragment {
            return ContactsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}