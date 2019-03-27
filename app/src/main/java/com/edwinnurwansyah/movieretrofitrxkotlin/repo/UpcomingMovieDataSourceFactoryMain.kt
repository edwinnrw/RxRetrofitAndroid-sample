package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import io.reactivex.disposables.CompositeDisposable


class UpcomingMovieDataSourceFactoryMain(private val compositeDisposable: CompositeDisposable) : DataSource.Factory<String,Movie>(){
    private val movieDataSourceLiveData = MutableLiveData<UpcomingMovieDataSourceMain>()

    override fun create(): DataSource<String, Movie> {
        var movieDataSource=UpcomingMovieDataSourceMain(compositeDisposable)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
    fun getMutableLiveData():MutableLiveData<UpcomingMovieDataSourceMain>{
        return  movieDataSourceLiveData
    }

}