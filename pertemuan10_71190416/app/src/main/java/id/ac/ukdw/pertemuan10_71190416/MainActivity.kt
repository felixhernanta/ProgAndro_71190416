package id.ac.ukdw.pertemuan10_71190416

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var dbhelper: SQLiteOpenHelper?= null
    var db: SQLiteDatabase? = null
//    var listPenduduk = ArrayList<String>()
//    var adapter: PendudukAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbhelper= DatabaseHelper(this)
        db=dbhelper?.writableDatabase

        val btnSimpan= findViewById<Button>(R.id.btnSimpan)
        val btnHapus= findViewById<Button>(R.id.btnhapus)
        val btnCari= findViewById<Button>(R.id.btnCari)

        val editNama= findViewById<EditText>(R.id.editNama)
        val editUsia= findViewById<EditText>(R.id.editUsia)

        btnSimpan.setOnClickListener {
            saveData(editNama.text.toString(), editUsia.text.toString())
            editNama.setText("")
            editUsia.setText("")
        }
        btnHapus.setOnClickListener {
            deleteData(editNama.text.toString(), editUsia.text.toString())
            editNama.setText("")
            editUsia.setText("")
        }
        btnCari.setOnClickListener {
            if ((editNama.text.toString()=="" && editUsia.text.toString() == "") || (editNama.text.toString()== null && editUsia.text.toString() == null)){
                reloadData()
            }
            else{
                cariData(editNama.text.toString(), editUsia.text.toString())
            }
        }
        reloadData()
    }

    fun saveData(nama: String, usia: String){
        val values: ContentValues=ContentValues().apply {
            put(DatabaseContract.Penduduk.COLUMN_NAME_NAMA,nama)
            put(DatabaseContract.Penduduk.COLUMN_NAME_USIA,usia)
        }
        db?.insert(DatabaseContract.Penduduk.TABLE_NAME, null, values)
        reloadData()
    }

    fun deleteData(nama: String, usia: String){
        val selection = "${DatabaseContract.Penduduk.COLUMN_NAME_NAMA} LIKE ? OR "+
                "${DatabaseContract.Penduduk.COLUMN_NAME_USIA} LIKE ?"
        val selectionArgs = arrayOf(nama, usia)
        val deletedRows = db?.delete(DatabaseContract.Penduduk.TABLE_NAME, selection, selectionArgs)
        reloadData()
    }

    @SuppressLint("Range")
    fun cariData(nama: String, usia: String){
        val db= dbhelper?.readableDatabase
        val columns= arrayOf(BaseColumns._ID,
            DatabaseContract.Penduduk.COLUMN_NAME_NAMA,
            DatabaseContract.Penduduk.COLUMN_NAME_USIA)
        val selection = "${DatabaseContract.Penduduk.COLUMN_NAME_NAMA} LIKE ? OR "+
                "${DatabaseContract.Penduduk.COLUMN_NAME_USIA} LIKE ?"
        val selectionArgs = arrayOf(nama, usia)
        val sortOrder = "${DatabaseContract.Penduduk.COLUMN_NAME_NAMA} ASC "
        val cursor= db?.query(
            DatabaseContract.Penduduk.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder// "NAMA ASC"
        )
        var result=""
        with(cursor!!){
            while (moveToNext()){
                result += "${getString(getColumnIndex(DatabaseContract.Penduduk.COLUMN_NAME_NAMA))}"+
                        "-${getString(getColumnIndex(DatabaseContract.Penduduk.COLUMN_NAME_USIA))}\n"
            }
            val tvHasil= findViewById<TextView>(R.id.tvHasil)
            tvHasil.text=result
        }
        cursor.close()
    }


    //!!!!! bagian cursor untuk pencarian
    @SuppressLint("Range")
    fun reloadData(){

        val columns = arrayOf(
            BaseColumns._ID,
            DatabaseContract.Penduduk.COLUMN_NAME_NAMA,
            DatabaseContract.Penduduk.COLUMN_NAME_USIA
        )


        val cursor= db?.query(
            DatabaseContract.Penduduk.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            "${DatabaseContract.Penduduk.COLUMN_NAME_NAMA} ASC "  // "NAMA ASC"
        )


        var result=""
        with(cursor!!){
            while (moveToNext()){
                result += "${getString(getColumnIndex(DatabaseContract.Penduduk.COLUMN_NAME_NAMA))}"+
                        "-${getString(getColumnIndex(DatabaseContract.Penduduk.COLUMN_NAME_USIA))}\n"
            }
            val tvHasil= findViewById<TextView>(R.id.tvHasil)
            tvHasil.text=result
        }

    }
}