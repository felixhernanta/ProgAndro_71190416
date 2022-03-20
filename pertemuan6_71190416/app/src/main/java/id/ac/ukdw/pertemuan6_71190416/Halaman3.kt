package id.ac.ukdw.pertemuan6_71190416

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Halaman3 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)
        val buttonclick = findViewById<Button>(R.id.button)
        buttonclick.setOnClickListener {
            moveAnotherActivity()
        }
    }

    fun moveAnotherActivity(){
        val i: Intent= Intent(this, Halaman1::class.java)
        startActivity(i)
        finish()
    }
}