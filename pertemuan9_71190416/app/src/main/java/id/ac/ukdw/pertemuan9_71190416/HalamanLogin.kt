package id.ac.ukdw.pertemuan9_71190416

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
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
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halamanlogin)

        var sp: SharedPreferences? = getSharedPreferences("database1", MODE_PRIVATE)
        editor = sp?.edit()
        var testing1: Int?= sp?.getInt("LastClick",0)
        val checklogin = sp?.getBoolean("isLogin", false)
        currentLanguage=intent.getStringExtra(currentLanguageNew).toString()

        when(testing1){
            0-> setLocale("en")
            1-> setLocale("in")
            2-> setLocale("ja")
        }

        var testing2: Int?= sp?.getInt("LastClick1",0)
        when(testing2){
            0-> setukuranhuruf("kecil")
            1-> setukuranhuruf("sedang")
            2-> setukuranhuruf("besar")
        }

        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val login = findViewById<Button>(R.id.button2)
        login.setOnClickListener {
            loginpressed(username.text.toString(), password.text.toString())

        }
    }

    fun loginpressed(user: String, pass: String){
       if (pass=="1234"){
           editor?.putBoolean("isLogin",true)
           editor?.commit()
           val i: Intent = Intent(this, MainActivity::class.java)
           startActivity(i)
           finish()
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
    fun setukuranhuruf(ukuran : String){
        val title = findViewById<TextView>(R.id.textView)
        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val login = findViewById<Button>(R.id.button2)
        val register = findViewById<Button>(R.id.button3)
        val notification = findViewById<TextView>(R.id.textView4)

        if (ukuran=="kecil"){
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
            username.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            register.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            notification.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        }
        else if (ukuran=="sedang"){
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            username.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            register.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            notification.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        }
        else if (ukuran=="besar"){
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35f)
            username.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            password.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            login.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            register.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            notification.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }
    }
}
