package com.edwinnurwansyah.movieretrofitrxkotlin.model

data class WrapperMovie2(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    var results: List<Movie2>
)