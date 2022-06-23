package id.ac.ukdw.a71190416_final

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.internal.ContextUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UpdateMusic : AppCompatActivity() {
    val cal = Calendar.getInstance()
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updatemusic)

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val googleClient = GoogleSignIn.getClient(this, signInOptions)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        firestore = FirebaseFirestore.getInstance()

        val accept = findViewById<Button>(R.id.buttonUpdate)
        val decline = findViewById<Button>(R.id.buttonDecline)

        val ambiljudul =intent.getStringExtra("judul")
        val ambilpenyanyi=intent.getStringExtra("penyanyi")
        val ambilalbum=intent.getStringExtra("album")
        val ambilgenre=intent.getStringExtra("genre")

        val judul = findViewById<TextView>(R.id.editTextJudulLagu)
        val penyanyi = findViewById<EditText>(R.id.editTextPenyanyi)
        val album = findViewById<EditText>(R.id.editTextAlbum)
        val genre = findViewById<EditText>(R.id.editTextGenre)
        val kalendar= findViewById<DatePicker>(R.id.TanggalRelease)

        val bundle: Bundle = intent.extras!!
        judul.text = ambiljudul
        penyanyi.setText(ambilpenyanyi)
        album.setText(ambilalbum)
        genre.setText(ambilgenre)

        decline.setOnClickListener {
            val i = Intent(this, LoginScreen::class.java)
            startActivity(i)
            finish()
        }
        accept.setOnClickListener {

            if (judul!=null && penyanyi!=null && album!=null && genre!=null && kalendar!=null){
                if (acct!=null){
                    val personName = acct.email
                    val kalendar2 = kalendar.dayOfMonth.toString() +  " / " + kalendar.month + " / "+ kalendar.year
                    val albumLagu = AlbumLagu(judul.text.toString(), penyanyi.text.toString(), album.text.toString(), genre.text.toString(), kalendar2, personName.toString())
                    firestore?.collection(personName.toString())
                        ?.document(judul.text.toString())
                        ?.set(albumLagu)
                        ?.addOnSuccessListener {
                            val i = Intent(this, LoginScreen::class.java)
                            startActivity(i)
                            finish()
                            Toast.makeText(
                                this,
                                "Proses Perubahan Berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                        }?.addOnFailureListener {
                            Toast.makeText(this, "Proses Perubahan Gagal", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
            else{
                Toast.makeText(this, "Data harus diisi semua", Toast.LENGTH_SHORT).show()
            }
        }
    }
}