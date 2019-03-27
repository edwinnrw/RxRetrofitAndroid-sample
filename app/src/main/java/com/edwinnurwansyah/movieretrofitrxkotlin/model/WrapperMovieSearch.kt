package com.edwinnurwansyah.movieretrofitrxkotlin.model

data class WrapperMovieSearch(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    var results: List<MovieSearch>
)