package id.ac.ukdw.a71190416_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongDetail : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detaillagu)

        val judul = findViewById<TextView>(R.id.judul)
        val penyanyi = findViewById<TextView>(R.id.penyanyi)
        val album = findViewById<TextView>(R.id.album)
        val genre = findViewById<TextView>(R.id.genre)
        val tanggal = findViewById<TextView>(R.id.tanggal)
        val gambar = findViewById<ImageView>(R.id.foto)

        val ambiljudul =intent.getStringExtra("judul")
        val ambilpenyanyi=intent.getStringExtra("penyanyi")
        val ambilalbum=intent.getStringExtra("album")
        val ambilgenre=intent.getStringExtra("genre")
        val ambiltanggal=intent.getStringExtra("tanggal")

        gambar.setImageResource(R.drawable.musiclogo)
        judul.text=ambiljudul
        penyanyi.text=ambilpenyanyi
        album.text=ambilalbum
        genre.text=ambilgenre
        tanggal.text=ambiltanggal

        val kembali = findViewById<Button>(R.id.buttonBack)
        kembali.setOnClickListener {
            val i = Intent(this, LoginScreen::class.java)
            startActivity(i)
            finish()
        }
    }
}