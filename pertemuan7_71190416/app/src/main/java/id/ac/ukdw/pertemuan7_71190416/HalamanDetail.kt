package id.ac.ukdw.pertemuan7_71190416

import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HalamanDetail : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layoutdetail)
        val cetakgambar = findViewById<ImageView>(R.id.foto)
//        val ambilgambar=intent.getStringExtra("foto")
        val cetaknama = findViewById<TextView>(R.id.namaOrang)
        val ambilnama=intent.getStringExtra("nama")
        val cetaknotelp = findViewById<TextView>(R.id.noTelepon)
        val ambilnotelp=intent.getStringExtra("notelp")
        val bundle: Bundle = intent.extras!!
        val ambilgambar=bundle.getInt("foto")
        cetakgambar.setImageResource(ambilgambar)
        cetaknama.text=ambilnama
        cetaknotelp.text=ambilnotelp
    }
}