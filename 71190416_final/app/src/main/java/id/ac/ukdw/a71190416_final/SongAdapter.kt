package id.ac.ukdw.a71190416_final

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class SongAdapter (var listSong: ArrayList<AlbumLagu>, var context: Context): RecyclerView.Adapter<SongAdapter.SongHolder>() {
    var firestore: FirebaseFirestore? = null

    val mainList = listSong
    val searchList = ArrayList<AlbumLagu>(listSong)

    class SongHolder(val view : View): RecyclerView.ViewHolder(view){

        @SuppressLint("RestrictedApi")
        fun bind(lagu: AlbumLagu, context: Context){
            val firestore = FirebaseFirestore.getInstance()
            view.findViewById<TextView>(R.id.judulLagu).setText(lagu.judul)
            view.findViewById<TextView>(R.id.namaPenyanyi).setText(lagu.penyanyi)
            view.setOnClickListener{
                val i: Intent = Intent(view.context, SongDetail::class.java)
                i.putExtra("judul",lagu.judul)
                i.putExtra("penyanyi",lagu.penyanyi)
                i.putExtra("album",lagu.album)
                i.putExtra("genre",lagu.genre)
                i.putExtra("tanggal",lagu.tanggal)
                context.startActivity(i)
            }
            val buttonUpdate = view.findViewById<Button>(R.id.buttonUpdate)
            buttonUpdate.setOnClickListener {
                val i: Intent = Intent(view.context, UpdateMusic::class.java)
//                i.putExtra("foto",lagu.foto)
                i.putExtra("judul",lagu.judul)
                i.putExtra("penyanyi",lagu.penyanyi)
                i.putExtra("album",lagu.album)
                i.putExtra("genre",lagu.genre)
                i.putExtra("tanggal",lagu.tanggal)
                context.startActivity(i)
            }
            val buttonHapus = view.findViewById<Button>(R.id.buttonDelete)
            buttonHapus.setOnClickListener {
                firestore?.collection(lagu.akun)
                    ?.document(lagu.judul)?.delete()
                    ?.addOnSuccessListener {
                        val i: Intent = Intent(view.context, LoginScreen::class.java)
                        context.startActivity(i)
                        getActivity(view.context)?.finish()
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragmentlagu, parent, false)
        return SongHolder(v)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bind(listSong[position],context)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }
}