package com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.edwinnurwansyah.movieretrofitrxkotlin.model.BaseResponse
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.repo.TrendingMovieDataSource
import com.edwinnurwansyah.movieretrofitrxkotlin.repo.TrendingMovieDataSourceFactoryMain
import com.edwinnurwansyah.movieretrofitrxkotlin.repo.TrendingMovieDataSourceMain
import io.reactivex.disposables.CompositeDisposable

class TrendingViewModel: ViewModel() {


    lateinit var itemPagedList: LiveData<PagedList<Movie>>
    private val compositeDisposable = CompositeDisposable()
    private var progressLoadStatus: LiveData<BaseResponse> = MutableLiveData()

    fun fetchTrending(){
        var movieDataSourceFactory = TrendingMovieDataSourceFactoryMain(compositeDisposable)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(TrendingMovieDataSource.PAGE_SIZE).build()
        itemPagedList = LivePagedListBuilder(movieDataSourceFactory, pagedListConfig).build()
        progressLoadStatus = Transformations.switchMap(
            movieDataSourceFactory.getMutableLiveData(),TrendingMovieDataSourceMain::getProgressLiveStatus)

    }

    fun getProgressLoadStatus(): LiveData<BaseResponse> {
        return progressLoadStatus
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}