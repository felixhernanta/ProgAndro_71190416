package id.ac.ukdw.pertemuan5_71190416

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.inputusername)
        val password = findViewById<EditText>(R.id.inputpassword)
        val login = findViewById<Button>(R.id.buttonlogin)
        login.setOnClickListener {
            loginpressed(username.text.toString(), password.text.toString())
        }
    }

    fun loginpressed(user: String, pass: String){
        if (user=="" || user.equals(null)){
            showNotification("Username tidak boleh kosong")
        }
        else if (pass=="1234"){
            val i: Intent= Intent(this, MainActivity::class.java)
            i.putExtra("username",user)
            startActivity(i)
        }
        else{
            showNotification("Passwordnya salah !!!")
        }
    }
    fun showNotification(notifmessage: String) {
        Toast.makeText(this, notifmessage, Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        setContentView(R.layout.activity_login)
    }
}