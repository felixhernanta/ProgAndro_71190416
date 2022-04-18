package id.ac.ukdw.pertemuan7_71190416

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter (var listContact: ArrayList<Contact>, var context: Context): RecyclerView.Adapter<ContactAdapter.ContactHolder>(){
    class ContactHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bind(contact: Contact, context: Context){
            view.findViewById<ImageView>(R.id.foto).setImageResource(contact.foto)
            view.findViewById<TextView>(R.id.namaOrang).setText(contact.title)
            view.setOnClickListener{
//                Toast.makeText(context, contact.title, Toast.LENGTH_SHORT).show()
                val i: Intent= Intent(view.context, HalamanDetail::class.java)
                i.putExtra("foto",contact.foto)
                i.putExtra("nama",contact.title)
                i.putExtra("notelp",contact.kontak)
                context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_kontak, parent, false)
        return ContactHolder(v)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(listContact[position],context)
    }

    override fun getItemCount(): Int {
        return listContact.size
    }
}