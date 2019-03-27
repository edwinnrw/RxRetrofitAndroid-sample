package com.edwinnurwansyah.movieretrofitrxkotlin.model

data class Movie2(
    val id: String, val title: String, val poster_path: String, val original_title: String, val backdrop_path: String?,
    val overview: String, val release_date: String, val vote_average:String, var genre:List<Genre>,var runtime:String?
)