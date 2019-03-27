package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import io.reactivex.disposables.CompositeDisposable


class TrendingMovieDataSourceFactoryMain(private val compositeDisposable: CompositeDisposable) :
    DataSource.Factory<String,Movie>(){


    private val movieDataSourceLiveData = MutableLiveData<TrendingMovieDataSourceMain>()

    override fun create(): DataSource<String, Movie> {
        var movieDataSource=TrendingMovieDataSourceMain(compositeDisposable)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun getMutableLiveData():MutableLiveData<TrendingMovieDataSourceMain>{
        return  movieDataSourceLiveData
    }

}