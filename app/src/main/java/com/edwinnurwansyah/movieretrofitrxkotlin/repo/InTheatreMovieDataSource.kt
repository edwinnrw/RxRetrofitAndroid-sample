package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.edwinnurwansyah.movieretrofitrxkotlin.BuildConfig
import com.edwinnurwansyah.movieretrofitrxkotlin.api.Endpoint
import com.edwinnurwansyah.movieretrofitrxkotlin.api.ServiceHelper
import com.edwinnurwansyah.movieretrofitrxkotlin.model.BaseResponse
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperMovie
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class InTheatreMovieDataSource : PageKeyedDataSource<Int,Movie>(){

    private val compositeDisposable=CompositeDisposable()

    companion object {
        var PAGE_SIZE:Int=1
        var FIRS_PAGE=1
        var CURRENT_PAGE= FIRS_PAGE

    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val query = HashMap<String, String>()
        query["api_key"] = BuildConfig.API_KEY

        compositeDisposable.add(
            ServiceHelper.createService(Endpoint::class.java).getNowPlaying(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    var gson=Gson()
                    gson.fromJson(it,WrapperMovie::class.java)
                }
                .subscribe(
                    {
                        PAGE_SIZE =it.total_pages
                        callback.onResult(it.results,null,FIRS_PAGE+1)

                    },
                    {
                        Log.d("Tes",it.message)
                        BaseResponse.error(it)


                    }
                )

        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }


}