package com.example.randommovie

data class Movie(
    val title: String,
    val director: String,
    val year: Int,
    val rating: Float,
    val posterUrl: String,
    val actors: Array<String>
) {
}