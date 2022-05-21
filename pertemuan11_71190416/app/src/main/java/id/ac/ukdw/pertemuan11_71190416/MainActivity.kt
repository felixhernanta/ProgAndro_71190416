package id.ac.ukdw.pertemuan11_71190416

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    var clickCount: Int = 0
    var arrayTampung = arrayListOf<String>()
    var sortBy: String = "nim"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore = FirebaseFirestore.getInstance()

        val editNama = findViewById<EditText>(R.id.editNama)
        val editNIM = findViewById<EditText>(R.id.editNIM)
        val editIPK = findViewById<EditText>(R.id.editIPK)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnHapus = findViewById<Button>(R.id.btnhapus)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val btnUrut = findViewById<Button>(R.id.btnSort)
        val pilihan = resources.getStringArray(R.array.pilihanurutan)
        val spinnerpilihan = findViewById<Spinner>(R.id.spinnerPilihan)

        if (spinnerpilihan != null) {
            val adapter = ArrayAdapter<Any?>(this, R.layout.spinnerurutan, pilihan)
            spinnerpilihan.adapter = adapter
            pengurutan()
        }

        btnSimpan.setOnClickListener {
            if (TextUtils.isEmpty(editNama.text.toString()) ||
                TextUtils.isEmpty(editNIM.text.toString()) ||
                TextUtils.isEmpty(editIPK.text.toString())
            ) {
                Toast.makeText(this, "Semua Data Harus Diisi", Toast.LENGTH_SHORT).show()
            } else {
                if (editNIM.text.toString().replace("\\D", "")
                        .isEmpty() && editNIM.text.toString().length == 8
                ) {
                    Toast.makeText(this, "Salah Format NIM", Toast.LENGTH_SHORT).show()
                } else if (editIPK.text.toString().toDouble() < 0 || editIPK.text.toString()
                        .toDouble() > 4
                ) {
                    Toast.makeText(this, "Salah Format IPK", Toast.LENGTH_SHORT).show()
                } else {
                    val mahasiswa = Mahasiswa(
                        editNama.text.toString(),
                        editNIM.text.toString(),
                        editIPK.text.toString().toDouble()
                    )
                    if (firestore?.collection("namaMahasiswa")
                            ?.document(editNIM.text.toString()) == null
                    ) {
                        Toast.makeText(this, "NIK sudah ada", Toast.LENGTH_SHORT).show()
                    } else {
                        firestore?.collection("namaMahasiswa")?.document(mahasiswa.nim)
                            ?.set(mahasiswa)
                            ?.addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Proses Penyimpanan Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }?.addOnFailureListener {
                                Toast.makeText(this, "Proses Penyimpanan Gagal", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        editNama.setText("")
                        editNIM.setText("")
                        editIPK.setText("")
                        refreshData()
                    }
                }
            }
        }

        btnCari.setOnClickListener {
            var cariNama: String = ""
            var cariNIM: String = ""
            var cariIPK: String = ""

            if (!TextUtils.isEmpty(editNama.text.toString())) {
                cariNama = editNama.text.toString()
            }
            if (!TextUtils.isEmpty(editNIM.text.toString())) {
                cariNIM = editNIM.text.toString()
            }
            if (!TextUtils.isEmpty(editIPK.text.toString())) {
                cariIPK = editIPK.text.toString()
            }
            cari(cariNama, cariNIM, cariIPK)
        }

        btnUrut.setOnClickListener {
            clickCount += 1
            if (clickCount % 2 == 0) {
                val btnUrut = findViewById<Button>(R.id.btnSort)
                btnUrut.setText("Urutkan (ASC)")
            } else {
                btnUrut.setText("Urutkan (DESC)")
            }
            refreshData()
        }

        btnHapus.setOnClickListener {
            if (editNama.text.toString().isNotEmpty()) {
                firestore?.collection("namaMahasiswa")
                    ?.get()
                    ?.addOnSuccessListener { result ->
                        for (doc in result) {
                            if (doc!!.get("nama").toString()
                                    .contains(editNama.text.toString(), ignoreCase = true)
                            ) {
//                                Toast.makeText(this, doc!!.get("nik").toString(), Toast.LENGTH_SHORT).show()
                                firestore?.collection("namaMahasiswa")
                                    ?.document(doc!!.get("nim").toString())?.delete()
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            editNama.text.toString() + " Berhasil Dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            editNama.text.toString() + " Gagal Dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }
            }
            refreshData()
        }

        refreshData()
    }

    fun refreshData() {
        firestore?.collection("namaMahasiswa")
            ?.get()
            ?.addOnSuccessListener { result ->
                val txvHasil = findViewById<TextView>(R.id.txvHasil)
                val tampungpilihan = arrayListOf<String>()
                val tampungpilihan1 = arrayListOf<String>()
                var hasil = ""
                var hitung = 0
                for (doc in result) {
                    hitung+=1
                }
//                Toast.makeText(this, arrayTampung.size.toString(), Toast.LENGTH_SHORT).show()
                if (arrayTampung.size==0 || arrayTampung.size==hitung){
                    arrayTampung.clear()
                    for (doc in result) {
                        arrayTampung.add("${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n")
                        if (sortBy == "nim") {
                            tampungpilihan.add("${doc!!.get("nim")}")
                        } else if (sortBy == "nama") {
                            tampungpilihan.add("${doc!!.get("nama")}")
                        } else if (sortBy == "ipk") {
                            tampungpilihan.add("${doc!!.get("ipk")}")
                        }
                    }
                }
                else{
                    for (doc in result) {
                        if (sortBy == "nim") {
                            tampungpilihan.add("${doc!!.get("nim")}")
                        } else if (sortBy == "nama") {
                            tampungpilihan.add("${doc!!.get("nama")}")
                        } else if (sortBy == "ipk") {
                            tampungpilihan.add("${doc!!.get("ipk")}")
                        }
                    }
                }

                if (clickCount % 2 == 0) {
                    var tampungpilihan = tampungpilihan.sorted()
                    for (item in tampungpilihan) {
                        for (item1 in arrayTampung){
                            if (item1.contains(item)){
                                tampungpilihan1.add(item1)
                                arrayTampung.remove(item1)
                                break
                            }
                        }
                    }
//                    Toast.makeText(this, tampungpilihan.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    var tampungpilihan = tampungpilihan.sortedDescending()
                    for (item in tampungpilihan) {
                        for (item1 in arrayTampung){
                            if (item1.contains(item)){
                                tampungpilihan1.add(item1)
                                arrayTampung.remove(item1)
                                break
                            }
                        }
                    }
//                    Toast.makeText(this, tampungpilihan.toString(), Toast.LENGTH_SHORT).show()
                }


                arrayTampung.clear()
                arrayTampung = tampungpilihan1
                for (cetak in arrayTampung){
                    hasil += cetak
                }
                txvHasil.setText(hasil)
            }
    }

    fun pengurutan() {
        val spinnerpilihan = findViewById<Spinner>(R.id.spinnerPilihan)
        spinnerpilihan.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> sortBy = "nim"
                    1 -> sortBy = "nama"
                    2 -> sortBy = "ipk"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        refreshData()
    }


    fun cari(nama: String, nim: String, ipk: String) {
        firestore?.collection("namaMahasiswa")
            ?.get()
            ?.addOnSuccessListener { result ->
                val txvHasil = findViewById<TextView>(R.id.txvHasil)
                val arrayCari = arrayListOf<String>()
                var hasil1 = ""
                for (doc in result) {
                    if (nama == "" && nim == "" && ipk == "") {
                        arrayCari.add("${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n")

                    } else {
                        if (nama != "" && nim != "" && ipk != "") {
                            if (doc!!.get("nama").toString().contains(nama, ignoreCase = true) &&
                                doc!!.get("nim").toString().contains(nim, ignoreCase = true) &&
                                doc!!.get("ipk").toString().contains(ipk, ignoreCase = true)
                            ) {
                                var hasil =
                                    "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                for (item in arrayTampung){
                                    if (hasil == item){
                                        arrayCari.add(item)
                                    }
                                }
                            }
                        } else {
                            if (nama != "" && nim != "") {
                                if (doc!!.get("nama").toString()
                                        .contains(nama, ignoreCase = true) &&
                                    doc!!.get("nim").toString().contains(nim, ignoreCase = true)
                                ) {
                                    var hasil =
                                        "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                    for (item in arrayTampung){
                                        if (hasil == item){
                                            arrayCari.add(item)
                                        }
                                    }
                                }
                            } else if (nama != "" && ipk != "") {
                                if (doc!!.get("nama").toString()
                                        .contains(nama, ignoreCase = true) &&
                                    doc!!.get("ipk").toString().contains(ipk, ignoreCase = true)
                                ) {
                                    var hasil =
                                        "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                    for (item in arrayTampung){
                                        if (hasil == item){
                                            arrayCari.add(item)
                                        }
                                    }
                                }
                            } else if (ipk != "" && nim != "") {
                                if (doc!!.get("nim").toString().contains(nim, ignoreCase = true) &&
                                    doc!!.get("ipk").toString().contains(ipk, ignoreCase = true)
                                ) {
                                    var hasil =
                                        "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                    for (item in arrayTampung){
                                        if (hasil == item){
                                            arrayCari.add(item)
                                        }
                                    }
                                }
                            } else {
                                if (nama != "") {
                                    if (doc!!.get("nama").toString()
                                            .contains(nama, ignoreCase = true)
                                    ) {
                                        var hasil =
                                            "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                        for (item in arrayTampung){
                                            if (hasil == item){
                                                arrayCari.add(item)
                                            }
                                        }
                                    }
                                } else if (nim != "") {
                                    if (doc!!.get("nim").toString()
                                            .contains(nim, ignoreCase = true)
                                    ) {
                                        var hasil =
                                            "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                        for (item in arrayTampung){
                                            if (hasil == item){
                                                arrayCari.add(item)
                                            }
                                        }
                                    }
                                } else if (ipk != "") {
                                    if (doc!!.get("ipk").toString()
                                            .contains(ipk, ignoreCase = true)
                                    ) {
                                        var hasil =
                                            "${doc!!.get("nim")} - ${doc!!.get("nama")} - ${doc!!.get("ipk")}\n"
                                        for (item in arrayTampung){
                                            if (hasil == item){
                                                arrayCari.add(item)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//                for (item in arrayCari) {
//                    hasil1 += item
//                }
                arrayTampung=arrayCari
//                txvHasil.setText(hasil1)
                refreshData()
//                Toast.makeText(this, arrayTampung.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}