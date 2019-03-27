package com.edwinnurwansyah.movieretrofitrxkotlin.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.edwinnurwansyah.movieretrofitrxkotlin.BuildConfig
import com.edwinnurwansyah.movieretrofitrxkotlin.api.Endpoint
import com.edwinnurwansyah.movieretrofitrxkotlin.api.ServiceHelper
import com.edwinnurwansyah.movieretrofitrxkotlin.model.*
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.HashMap
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS



class SearchViewModel: ViewModel() {


    private val compositeDisposable = CompositeDisposable()
    private var progressLiveStatus: MutableLiveData<BaseResponse> = MutableLiveData()
    private var wrapperMovieData: MutableLiveData<WrapperMovie2> = MutableLiveData()
    var movieData: MutableLiveData<Movie2> = MutableLiveData()

    fun fetchSearch(querys:String) {
        if (!querys.isEmpty()){
            var  movieObservable:ConnectableObservable<JsonElement> = getMovie(querys).replay()
            compositeDisposable.add(
                movieObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        progressLiveStatus.postValue(BaseResponse.loading())
                    }
                    .map {
                        var gson = Gson()
                        gson.fromJson(it, WrapperMovie2::class.java)
                    }
                    .subscribeWith(object : DisposableObserver<WrapperMovie2>(){
                        override fun onComplete() {
                            progressLiveStatus.postValue(BaseResponse.success())

                        }

                        override fun onNext(t: WrapperMovie2) {
                            wrapperMovieData.postValue(t)

                        }

                        override fun onError(e: Throwable) {
                            progressLiveStatus.postValue(BaseResponse.error(e))
                        }

                    })

            )
            compositeDisposable.add(
                movieObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        var gson = Gson()
                        var wrapperMovie=gson.fromJson(it,WrapperMovie2::class.java)
                        return@flatMap Observable.fromIterable(wrapperMovie.results)
                    }
                    .flatMap {
                        return@flatMap getDetailMovie(it)
                    }

                    .subscribeWith(object : DisposableObserver<Movie2>(){
                        override fun onComplete() {

                        }

                        override fun onNext(t: Movie2) {
                            Log.d("TESGENRE NEXT",t.title)

                            movieData.value=t

                        }

                        override fun onError(e: Throwable) {
                            progressLiveStatus.postValue(BaseResponse.error(e))


                        }

                    })

            )

            movieObservable.connect()

        }
    }
    private fun getMovie(querys: String) :Observable<JsonElement>{
        val query = HashMap<String, String>()
        query["api_key"] = BuildConfig.API_KEY
        query["query"]=querys
        return ServiceHelper.createService(Endpoint::class.java).getSearchMovie(query).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getDetailMovie(it: Movie2): Observable<Movie2> {

        return ServiceHelper.createService(Endpoint::class.java).getDetailMovie(it.id)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .map {
                return@map Movie2(it.id,it.title,it.poster_path,it.original_title,
                    it.backdrop_path,it.overview,it.release_date,it.vote_average,it.genres,it.runtime)
            }
            .retryWhen {
                return@retryWhen it.flatMap {throwable->

                    return@flatMap when (throwable) {
                        is IOException -> // Retry code
                            // For example: retry after 5seconds
                            Observable.timer(5, TimeUnit.SECONDS).take(3)
                        else -> return@flatMap Observable.just(Observable.empty<Any>())
                    }

                }
            }



    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    fun getProgressLoadStatus(): LiveData<BaseResponse> {
        return progressLiveStatus
    }

    fun getWrapperMovie(): LiveData<WrapperMovie2>{
        return wrapperMovieData
    }


}