package id.ac.ukdw.a71190416_final

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginScreen : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginscreen)

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val googleClient = GoogleSignIn.getClient(this, signInOptions)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        val buttonLogOut = findViewById<Button>(R.id.logoutbutton)
        firestore = FirebaseFirestore.getInstance()
        buttonLogOut.setOnClickListener {
            googleClient.signOut().addOnSuccessListener{
                FirebaseAuth.getInstance().signOut()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        val buttonCreate = findViewById<Button>(R.id.createbutton)
        buttonCreate.setOnClickListener {
            val i = Intent(this, CreateMusic::class.java)
            startActivity(i)
        }

        if (acct!=null){
            val personName = acct.email
            if (personName != null) {
                firestore?.collection(personName)
                    ?.get()
                    ?.addOnSuccessListener {documents ->
                        val listSong = arrayListOf<AlbumLagu>()
                        for (document in documents){
                            listSong.add(AlbumLagu(
                                document["judul"].toString(),
                                document["penyanyi"].toString(),
                                document["album"].toString(),
                                document["genre"].toString(),
                                document["tanggal"].toString(),
                                document["akun"].toString()
                            ))
                        }
                        val showingSong = findViewById<RecyclerView>(R.id.showingSong)
                        showingSong.layoutManager=LinearLayoutManager(this)
                        val adapter = SongAdapter(listSong, this)
                        showingSong.adapter=adapter
                    }
            }

        }

    }
}