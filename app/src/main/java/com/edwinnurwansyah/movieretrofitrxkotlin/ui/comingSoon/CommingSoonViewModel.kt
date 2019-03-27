package com.edwinnurwansyah.movieretrofitrxkotlin.ui.comingSoon

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.edwinnurwansyah.movieretrofitrxkotlin.model.BaseResponse
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.repo.*
import io.reactivex.disposables.CompositeDisposable

class CommingSoonViewModel: ViewModel() {


    lateinit var itemPagedList: LiveData<PagedList<Movie>>
    private val compositeDisposable = CompositeDisposable()
    private var progressLoadStatus: LiveData<BaseResponse> = MutableLiveData()

    fun fetchComingSoon() {
        var movieDataSourceFactory = UpcomingMovieDataSourceFactoryMain(compositeDisposable)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(InTheatreMovieDataSourceMain.PAGE_SIZE).build()
        itemPagedList = LivePagedListBuilder(movieDataSourceFactory, pagedListConfig).build()
        progressLoadStatus = Transformations.switchMap(
            movieDataSourceFactory.getMutableLiveData(), UpcomingMovieDataSourceMain::getProgressLiveStatus)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    fun getProgressLoadStatus(): LiveData<BaseResponse> {
        return progressLoadStatus
    }



}