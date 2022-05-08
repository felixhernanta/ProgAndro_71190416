package id.ac.ukdw.pertemuan9_71190416

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(){
    var sp: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var currentLanguage = "en"
    var currentLanguageNew: String? = null
    lateinit var locale: Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("database1", MODE_PRIVATE)
        editor = sp?.edit()
        val lastClick=sp?.getInt("LastClick",0)
        val lastClick1=sp?.getInt("LastClick1",0)
        currentLanguage=intent.getStringExtra(currentLanguageNew).toString()

        val bahasa = resources.getStringArray(R.array.pilihan_bahasa)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, bahasa
            )
            spinner.adapter = adapter
            spinner.setSelection(lastClick!!)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when(position){
                        0-> setLocale("en")
                        1-> setLocale("in")
                        2-> setLocale("ja")
                    }

                    sp?.edit()?.putInt("LastClick", position)?.commit()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }

        val ukuranfont = resources.getStringArray(R.array.ukuranfont)
        val spinner1 = findViewById<Spinner>(R.id.spinner2)
        if (spinner1 != null) {
            val adapter1 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, ukuranfont
            )
            spinner1.adapter = adapter1
            spinner1.setSelection(lastClick1!!)

            spinner1.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id: Long) {
                    sp?.edit()?.putInt("LastClick1", position)?.commit()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
        val logout = findViewById<Button>(R.id.button3)
        logout.setOnClickListener {
            val i=Intent(this, HalamanLogin::class.java)
            startActivity(i)
            finish()
        }
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
            var refresh = Intent(this, MainActivity::class.java)
            refresh.putExtra(currentLanguageNew, localeName)
            startActivity(refresh)
            finish()
        }
        else{

        }
    }
}