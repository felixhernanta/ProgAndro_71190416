package id.ac.ukdw.pertemuan9_71190416

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(){
    var sp: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("database1", MODE_PRIVATE)
        editor = sp?.edit()
        val lastClick=sp?.getInt("LastClick",0)



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
                    Toast.makeText(this@MainActivity, bahasa[position], Toast.LENGTH_SHORT).show()
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

            spinner1.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        ukuranfont[position], Toast.LENGTH_SHORT
                    ).show()
                    sp?.edit()?.putString("ukuran", ukuranfont[position])?.commit()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }
}