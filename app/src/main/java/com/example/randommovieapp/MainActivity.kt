package com.example.randommovieapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.InputStreamReader
import java.util.*
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var movies: Array<String>
    private lateinit var remainingMovies: MutableList<String>
    private val r = Random()

    override fun onStart() {
        super.onStart()
        Log.d("mytag", "onStart()" )
    }

    override fun onStop() {
        super.onStop()
        Log.d("mytag", "onStop()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movies = resources.getStringArray(R.array.movies)
        remainingMovies = movies.toMutableList()


        Log.d("mytag", movies[0])

        // открываем файл
        val moviesStream = resources.openRawResource(R.raw.movies)
        val gson = Gson() // конвертор из JSON обратно
        val moviesData = gson.fromJson(InputStreamReader(moviesStream), Movies::class.java)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val editor = pref.edit()
        editor.putString("name", "Vasya")
        editor.apply()

        Log.d("mytag", "name:" + pref.getString("name", ""))
    }

    fun onNextClick(view: View) {
        val tvTitle = findViewById<TextView>(R.id.title)
        if (remainingMovies.isEmpty()) {
            tvTitle.text = resources.getString(R.string.warning)
            return
        }
        val index = r.nextInt(remainingMovies.size)
        tvTitle.text = remainingMovies.removeAt(index)
    }

    fun onNextActivity(view: View) {
        val intent = Intent(this, GameActivity::class.java)

        intent.putExtra("name", "Petya")
        intent.putExtra("age", 19)
        startActivity(intent)
    }

    fun onClearClick(view: View) {
        val tvTitle = findViewById<TextView>(R.id.title)
        tvTitle.text= ""
        remainingMovies.clear(); remainingMovies = movies.toMutableList()
    }
}
