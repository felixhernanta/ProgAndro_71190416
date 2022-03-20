package id.ac.ukdw.pertemuan6_71190416

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Halaman1 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonclick = findViewById<Button>(R.id.button)
        buttonclick.setOnClickListener {
            moveAnotherActivity()
        }
    }

    fun moveAnotherActivity(){
        val i: Intent= Intent(this, Halaman2::class.java)
        startActivity(i)
        finish()
    }
}