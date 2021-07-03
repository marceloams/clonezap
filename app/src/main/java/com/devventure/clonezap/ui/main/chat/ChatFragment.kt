//package com.devventure.clonezap.ui.main.contacts
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.RecyclerView
//import com.devventure.clonezap.databinding.FragmentMainBinding
//import com.devventure.clonezap.model.Contact
//import com.devventure.clonezap.repository.ChatRepository
//import com.devventure.clonezap.ui.main.chat.ChatActivity
//import com.devventure.clonezap.databinding.ActivityChatBinding
//import com.devventure.clonezap.databinding.FragmentChatBinding
//import com.devventure.clonezap.ui.main.chat.ChatAdapter
//import com.devventure.clonezap.ui.main.chat.ChatViewModel
//import com.devventure.clonezap.ui.main.placeholder.PageViewModel
//import com.devventure.clonezap.ui.main.placeholder.PlaceholderFragment
//
///**
// * A placeholder fragment containing a simple view.
// */
//class ChatFragment : Fragment() {
//
//    private lateinit var chatViewModel: ChatViewModel
//    private lateinit var chatId: String
//    private var _binding: FragmentChatBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
//            setIndex(arguments?.getInt(PlaceholderFragment.ARG_SECTION_NUMBER) ?: 1)
//        }
//
//
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
//
//        _binding = FragmentChatBinding.inflate(inflater, container, false)
//        val root = binding.root
//
//        val messagesList: RecyclerView = binding.messagesList
//        val adapter: ChatAdapter = ChatAdapter()
//        chatViewModel.messagesList.observe(viewLifecycleOwner, {
//            adapter.setMessagesList(it)
//        })
//
//        messagesList.adapter = adapter
//
//        return root
//    }
//    companion object {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private const val CHAT_ID: String = "chat_id"
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        @JvmStatic
//        fun newInstance(chatId: String): PlaceholderFragment {
//            return PlaceholderFragment().apply {
//                arguments = Bundle().apply {
//                    putString(CHAT_ID, sectionNumber)
//                }
//            }
//        }
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}