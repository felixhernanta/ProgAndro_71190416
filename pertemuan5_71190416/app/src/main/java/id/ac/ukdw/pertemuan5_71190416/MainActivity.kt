package id.ac.ukdw.pertemuan5_71190416

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ambilusername=intent.getStringExtra("username")
        val cetakusername = findViewById<TextView>(R.id.getuser)
        cetakusername.text="${ambilusername}"
        val keluar=findViewById<Button>(R.id.buttonlogout)
        keluar.setOnClickListener{
            val i=Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}