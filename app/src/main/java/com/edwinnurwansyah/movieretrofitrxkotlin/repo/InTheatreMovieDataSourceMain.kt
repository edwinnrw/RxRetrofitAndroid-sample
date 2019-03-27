package com.edwinnurwansyah.movieretrofitrxkotlin.repo

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.edwinnurwansyah.movieretrofitrxkotlin.BuildConfig
import com.edwinnurwansyah.movieretrofitrxkotlin.api.Endpoint
import com.edwinnurwansyah.movieretrofitrxkotlin.api.ServiceHelper
import com.edwinnurwansyah.movieretrofitrxkotlin.model.BaseResponse
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperGenre
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperMovie
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class InTheatreMovieDataSourceMain(val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<String, Movie>() {

    private var progressLiveStatus: MutableLiveData<BaseResponse> = MutableLiveData()
    private lateinit var genre: WrapperGenre

    companion object {
        var PAGE_SIZE: Int = 1
        var FIRS_PAGE = 1

    }

    fun getProgressLiveStatus(): MutableLiveData<BaseResponse> {
        return progressLiveStatus
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Movie>) {
        val query = HashMap<String, String>()
        query["api_key"] = BuildConfig.API_KEY

        compositeDisposable.add(
            ServiceHelper.createService(Endpoint::class.java).getNowPlaying(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progressLiveStatus.postValue(BaseResponse.loading())

                }
                .map {
                    var gson = Gson()
                    gson.fromJson(it, WrapperMovie::class.java)
                }
                .flatMap {
                    return@flatMap getGenreInital(it)
                }
                .subscribe({
                    callback.onResult(it.results, null, (FIRS_PAGE + 1).toString())
                    PAGE_SIZE = it.total_pages
                    progressLiveStatus.postValue(BaseResponse.success())

                },{
                    progressLiveStatus.postValue(BaseResponse.error(it))

                })


        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Movie>) {

        val query = HashMap<String, String>()
        query["page"] = params.key
        query["api_key"] = BuildConfig.API_KEY

        compositeDisposable.add(
            ServiceHelper.createService(Endpoint::class.java).getNowPlaying(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    var gson = Gson()
                    gson.fromJson(it, WrapperMovie::class.java)
                }
                .flatMap {
                    return@flatMap getGenre(it)
                }
                .subscribe({ it ->
                    callback.onResult(it.results, (params.key.toInt() + 1).toString())
                    TrendingMovieDataSourceMain.CURRENT_PAGE = (params.key + 1).toInt()
                    progressLiveStatus.postValue(BaseResponse.success())
                },{
                    progressLiveStatus.postValue(BaseResponse.error(it))
                })

        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Movie>) {

    }

    private fun getGenreInital(movies: WrapperMovie): Observable<WrapperMovie> {
        val query = HashMap<String, String>()
        query["api_key"] = BuildConfig.API_KEY
        return ServiceHelper.createService(Endpoint::class.java).getGenre(query).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }
            .map {
                genre = it
                var listMovieNew = mutableListOf<Movie>()
                for (i in movies.results) {
                    var listGenreNew = mutableListOf<String>()
                    for (j in i.genre_ids) {
                        for (z in it.genres) {
                            if (j.contentEquals(z.id)) {
                                listGenreNew.add(z.name)
                            }
                        }
                    }
                    i.genre_ids = listGenreNew
                    listMovieNew.add(i)
                }
                var wrapperMovie = movies
                wrapperMovie.results = listMovieNew
                return@map wrapperMovie

            }
            .doOnError { Log.d("TesErorGenre", it.message) }

    }

    private fun getGenre(movies: WrapperMovie): Observable<WrapperMovie> {
        return Observable.just(movies).map {
            var listMovieNew = mutableListOf<Movie>()
            for (i in movies.results) {
                var listGenreNew = mutableListOf<String>()
                for (j in i.genre_ids) {
                    for (z in genre.genres) {
                        if (j.contentEquals(z.id)) {
                            listGenreNew.add(z.name)
                        }
                    }
                }
                i.genre_ids = listGenreNew
                listMovieNew.add(i)
            }
            var wrapperMovie = movies
            wrapperMovie.results = listMovieNew
            return@map wrapperMovie

        }
    }
}