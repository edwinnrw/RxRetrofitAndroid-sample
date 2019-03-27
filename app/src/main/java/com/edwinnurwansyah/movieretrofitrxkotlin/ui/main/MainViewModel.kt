package com.edwinnurwansyah.movieretrofitrxkotlin.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.repo.*


class MainViewModel(application: Application) : AndroidViewModel(application) {


    private lateinit var liveDataSource:LiveData<PageKeyedDataSource<Int,Movie>>
    private lateinit var itemPagedList:LiveData<PagedList<Movie>>


    fun fetchTrending() : LiveData<PagedList<Movie>> {
        var movieDataSourceFactory=TrendingMovieDataSourceFactory()
        liveDataSource=movieDataSourceFactory.getItemLiveDataSource()
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(TrendingMovieDataSource.PAGE_SIZE).build()
        itemPagedList= LivePagedListBuilder(movieDataSourceFactory,pagedListConfig).build()
        return itemPagedList

    }

    fun fetchMovieNow():LiveData<PagedList<Movie>> {
        var movieDataSourceFactory=InTheatreMovieDataSourceFactory()
        liveDataSource=movieDataSourceFactory.getItemLiveDataSource()
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(InTheatreMovieDataSource.PAGE_SIZE).build()
        itemPagedList= LivePagedListBuilder(movieDataSourceFactory,pagedListConfig).build()
        return itemPagedList

    }
    fun fetchMovieUpcoming():LiveData<PagedList<Movie>> {
        var movieDataSourceFactory=UpcomingMovieDataSourceFactory()
        liveDataSource=movieDataSourceFactory.getItemLiveDataSource()
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(InTheatreMovieDataSource.PAGE_SIZE).build()
        itemPagedList= LivePagedListBuilder(movieDataSourceFactory,pagedListConfig).build()
        return itemPagedList

    }
}