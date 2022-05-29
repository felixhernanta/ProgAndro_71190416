package id.ac.ukdw.pertemuan12_71190416

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtKota = findViewById<EditText>(R.id.editTextKota)
        val btnCheck = findViewById<Button>(R.id.buttonCheck)

        btnCheck.setOnClickListener {
            if (!TextUtils.isEmpty(edtKota.text.toString())){
                cekCuaca(edtKota.text.toString())
            }
        }
    }
    fun cekCuaca(kota : String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=${kota}&appid=a8ba9b2ff462b4ce0483a576ca35c4ba"
        val request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                val data = JSONObject(response)
                val cuacaHariIni = data.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description")
                val suhuHariIni = data.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp")
                val txvHariIni = findViewById<TextView>(R.id.txvHasilHariIni)
                txvHariIni.text="Today = \n${cuacaHariIni} (${String.format("%.2f",suhuHariIni - 273.15).toDouble()}\u2103)"
                val cuacaBesok = data.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description")
                val suhuBesok = data.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp")
                val txvBesok = findViewById<TextView>(R.id.txvHasilBesok)
                txvBesok.text="Tommorow = \n${cuacaBesok} (${String.format("%.2f",suhuBesok - 273.15).toDouble()}\u2103)"
              val cuacaLusa = data.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description")
                val suhuLusa = data.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp")
                val txvLusa = findViewById<TextView>(R.id.txvHasilLusa)
                txvLusa.text="2 days later= \n${cuacaLusa} (${String.format("%.2f",suhuLusa - 273.15).toDouble()}\u2103)"
            },
            Response.ErrorListener {

            })
        queue.add(request)
    }
}