package id.ac.ukdw.pertemuan9_71190416

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import id.ac.ukdw.pertemuan9_71190416.MainActivity
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class HalamanLogin : AppCompatActivity(){
    lateinit var locale: Locale
    var currentLanguage = "en"
    var currentLanguageNew: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanlogin)

        var sp: SharedPreferences? = getSharedPreferences("database1", MODE_PRIVATE)
        var testing1: Int?= sp?.getInt("LastClick",0)
        currentLanguage=intent.getStringExtra(currentLanguageNew).toString()
//        Toast.makeText(this, testing1.toString(), Toast.LENGTH_SHORT).show()
        when(testing1){
            0-> setLocale("en")
            1-> setLocale("in")
            2-> setLocale("ja")
        }

        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val login = findViewById<Button>(R.id.button2)
        login.setOnClickListener {
            loginpressed(username.text.toString(), password.text.toString())
        }
    }

    fun loginpressed(user: String, pass: String){
        if (user=="" || user.equals(null)){
            showNotification()
        }
        else if (pass=="1234"){
            val i: Intent = Intent(this, MainActivity::class.java)
            i.putExtra("username",user)
            startActivity(i)
        }
        else{
            showNotification()
        }
    }
    fun showNotification() {
        var notification= ArrayList<String>()
        notification.add("Wrong username or password")
        notification.add("Salah username atau kata sandi")
        notification.add("間違ったユーザー名またはパスワード")
        val notifikasi= findViewById<TextView>(R.id.textView4)
        if (currentLanguage.equals("en")){
            notifikasi.text= notification[0]
        }
        else if (currentLanguage.equals("in")){
            notifikasi.text= notification[1]
        }
        else if (currentLanguage.equals("ja")){
            notifikasi.text= notification[2]
        }
    }

    override fun onRestart() {
        super.onRestart()
        setContentView(R.layout.halamanlogin)
    }

    fun setLocale(localeName : String){
        if (localeName!=currentLanguage){
//            Toast.makeText(this, localeName + currentLanguage, Toast.LENGTH_SHORT).show()
            locale = Locale(localeName)
            var res = resources
            var dm = res.displayMetrics
            var conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            var refresh = Intent(this, HalamanLogin::class.java)
            refresh.putExtra(currentLanguageNew, localeName)
            startActivity(refresh)
            finish()
        }
        else{

        }
    }
}
