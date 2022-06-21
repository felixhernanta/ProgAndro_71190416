package id.ac.ukdw.a71190416_final

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CreateMusic : AppCompatActivity() {
    val cal = Calendar.getInstance()
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createmusic)

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val googleClient = GoogleSignIn.getClient(this, signInOptions)
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        firestore = FirebaseFirestore.getInstance()

        val judul = findViewById<EditText>(R.id.editTextJudulLagu)
        val penyanyi = findViewById<EditText>(R.id.editTextPenyanyi)
        val album = findViewById<EditText>(R.id.editTextAlbum)
        val genre = findViewById<EditText>(R.id.editTextGenre)
        val kalendar= findViewById<DatePicker>(R.id.TanggalRelease)

        val accept = findViewById<Button>(R.id.buttonUpdate)
        val decline = findViewById<Button>(R.id.buttonDecline)

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
                    if (firestore?.collection(personName.toString())
                            ?.document(judul.text.toString()) == null){
                        Toast.makeText(this, "Lagu sudah ada", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        firestore?.collection(personName.toString())?.document(albumLagu.judul)
                            ?.set(albumLagu)
                            ?.addOnSuccessListener {
                                val i = Intent(this, LoginScreen::class.java)
                                startActivity(i)
                                finish()
                                Toast.makeText(
                                    this,
                                    "Proses Penyimpanan Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }?.addOnFailureListener {
                                Toast.makeText(this, "Proses Penyimpanan Gagal", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }
            }
            else{
                Toast.makeText(this, "Data harus diisi semua", Toast.LENGTH_SHORT).show()
            }
        }
    }
}