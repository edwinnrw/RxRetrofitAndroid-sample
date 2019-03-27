package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Genre
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie


class TrendingMovieDataSourceFactory() : DataSource.Factory<Int,Movie>(){
    var genres: List<Genre>?=null
    constructor(genres: List<Genre>) : this(){
        this.genres=genres
    }

    val movieDataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int,Movie>>()

    override fun create(): DataSource<Int, Movie> {
        var movieDataSource:TrendingMovieDataSource
        if (genres.isNullOrEmpty()){
            movieDataSource=TrendingMovieDataSource()
            movieDataSourceLiveData.postValue(movieDataSource)
        }else{
            Log.d("genreMasuk", genres!![0].name)

            movieDataSource=TrendingMovieDataSource(genres)
            movieDataSourceLiveData.postValue(movieDataSource)

        }
        return movieDataSource
    }
    fun getItemLiveDataSource():MutableLiveData<PageKeyedDataSource<Int,Movie>>{
        return  movieDataSourceLiveData
    }


}