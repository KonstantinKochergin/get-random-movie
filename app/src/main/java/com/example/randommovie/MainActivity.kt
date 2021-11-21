package com.example.randommovie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val coroutineContext: CoroutineContext = Dispatchers.IO + CoroutineName("MainActivityScope")
    private val scope = CoroutineScope(coroutineContext)

    private lateinit var movies: Movies
    private lateinit var moviesToShowIndexes: MutableList<Int>

    lateinit var titleTv: TextView
    lateinit var directorTv: TextView
    lateinit var yearTv: TextView
    lateinit var ratingTv: TextView
    lateinit var posterView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val moviesStream = resources.openRawResource(R.raw.movies)
        val gson = Gson() // конвертор из JSON обратно
        this.movies = gson.fromJson(InputStreamReader(moviesStream), Movies::class.java)

        titleTv = findViewById(R.id.titleTv)
        directorTv = findViewById(R.id.directorTv)
        yearTv = findViewById(R.id.yearTv)
        ratingTv = findViewById(R.id.ratingTv)
        posterView = findViewById(R.id.posterView)
    }

    override fun onStart() {
        Log.d("Movies", "MainActivity start")
        initMoviesToShowIndexes()
        super.onStart()
    }

    fun onNextMovieButtonClick(v: View) {
        nextMovie()
    }

    private fun nextMovie() {
        if (moviesToShowIndexes.size > 0) {
            val nextMovieIndex = Random.nextInt(0, moviesToShowIndexes.size)
            renderMovie(movies.movies[moviesToShowIndexes[nextMovieIndex]])
            moviesToShowIndexes.removeAt(nextMovieIndex)
        }
        else {
            val intent = Intent(this, MoviesOverActivity::class.java)
            startActivity(intent)
        }
    }

    private fun renderMovie(movie: Movie) {
        titleTv.text = movie.title
        directorTv.text = movie.director
        yearTv.text = movie.year.toString()
        ratingTv.text = movie.rating.toString()
        scope.launch {
            val bitmap: Bitmap = BitmapFactory.decodeStream(URL(movie.posterUrl).getContent() as InputStream)
            withContext(Dispatchers.Main) {
                posterView.setImageBitmap(bitmap)
            }
        }
    }

    private fun initMoviesToShowIndexes() {
        moviesToShowIndexes = this.movies.movies.mapIndexed { index, _ -> index }.toMutableList()
    }
}