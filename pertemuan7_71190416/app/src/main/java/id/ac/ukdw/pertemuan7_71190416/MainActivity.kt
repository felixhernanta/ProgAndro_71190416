package id.ac.ukdw.pertemuan7_71190416

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listContact = arrayListOf<Contact>()
        listContact.add(Contact("Nero Claudius","08211230005",R.mipmap.neroclaudius))
        listContact.add(Contact("Katou Danzo","08214560188",R.mipmap.katoudanzo))
        listContact.add(Contact("Ereshkigal","08217890196",R.mipmap.ereshkigal))

        val showingContact = findViewById<RecyclerView>(R.id.showingContact)
        showingContact.layoutManager=LinearLayoutManager(this)
        val adapter = ContactAdapter(listContact,this)
        showingContact.adapter=adapter
    }
}