package id.ac.ukdw.pertemuan9_71190416

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
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
        val checklogin = sp?.getBoolean("isLogin", false)
        currentLanguage=intent.getStringExtra(currentLanguageNew).toString()

        if(checklogin == false){
            val i = Intent(this, HalamanLogin::class.java)
            startActivity(i)
            finish()
        }
        else{
            val bahasa = resources.getStringArray(R.array.pilihan_bahasa)
            val spinner = findViewById<Spinner>(R.id.spinner)
            if (spinner != null) {
                val adapter = ArrayAdapter<Any?>(
                    this, R.layout.spinnerbahasa, bahasa
                )
                adapter.setDropDownViewResource(R.layout.spinnerbahasa)
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
                    R.layout.spinnerukuranfont, ukuranfont
                )
                adapter1.setDropDownViewResource(R.layout.spinnerukuranfont)
                spinner1.adapter = adapter1
                spinner1.setSelection(lastClick1!!)

                spinner1.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id: Long) {
                        val language = findViewById<TextView>(R.id.textView2)
                        val ukuranfont = findViewById<TextView>(R.id.textView3)
                        val spinnerbahasa = findViewById<TextView>(R.id.ukuranbahasa)
                        val spinnerukuranfont = findViewById<TextView>(R.id.ukuranhuruf)
                        val logout = findViewById<Button>(R.id.button3)
                        if (position.equals(0)){
                            language.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                            ukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                            logout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                            spinnerbahasa.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                            spinnerukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                        }
                        else if (position.equals(1)){
                            language.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                            ukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                            logout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                            spinnerbahasa.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                            spinnerukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                        }
                        else if (position.equals(2)){
                            language.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                            ukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                            logout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                            spinnerbahasa.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                            spinnerukuranfont.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        }
                        sp?.edit()?.putInt("LastClick1", position)?.commit()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
            }
//        if (sp?.getBoolean("isLogin",false)==true){
//
//        }
            val logout = findViewById<Button>(R.id.button3)
            logout.setOnClickListener {
                editor?.putBoolean("isLogin",false)
                editor?.commit()
                val i=Intent(this, HalamanLogin::class.java)
                startActivity(i)
                finish()
            }
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