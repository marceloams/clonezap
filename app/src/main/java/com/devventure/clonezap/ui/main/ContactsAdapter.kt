package com.devventure.clonezap.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devventure.clonezap.R
import com.devventure.clonezap.model.Contact
import org.w3c.dom.Text

class ContactsAdapter() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    var contacts: ArrayList<Contact> = ArrayList<Contact>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contactName: TextView = view.findViewById(R.id.contactName)
        private val contactDetails: TextView = view.findViewById(R.id.contactDetails)
        private val contactImage: ImageView = view.findViewById(R.id.contactImage)

        fun setUser(contact: Contact){
            contactName.text = contact.name
            contactDetails.text = contact.email
        }
    }

    fun setContactsList(contacts: ArrayList<Contact>){
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUser(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}