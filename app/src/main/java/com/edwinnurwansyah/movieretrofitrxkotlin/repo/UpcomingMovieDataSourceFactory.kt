package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie


class UpcomingMovieDataSourceFactory : DataSource.Factory<Int,Movie>(){
    val movieDataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int,Movie>>()

    override fun create(): DataSource<Int, Movie> {
        var movieDataSource=UpcomingMovieDataSource()
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
    fun getItemLiveDataSource():MutableLiveData<PageKeyedDataSource<Int,Movie>>{
        return  movieDataSourceLiveData
    }


}