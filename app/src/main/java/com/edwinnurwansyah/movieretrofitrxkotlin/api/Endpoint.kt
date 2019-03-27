package com.edwinnurwansyah.movieretrofitrxkotlin.api

import android.os.Build
import com.edwinnurwansyah.movieretrofitrxkotlin.BuildConfig
import com.edwinnurwansyah.movieretrofitrxkotlin.model.MovieDetail
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperGenre
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperMovie
import com.google.gson.JsonElement
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface Endpoint {
    @GET("./3/movie/now_playing")
    fun getNowPlaying(@QueryMap option:Map<String, String>) : Observable<JsonElement>

    @GET("./3/movie/upcoming")
    fun getUpcoming(@QueryMap option:Map<String, String>) : Observable<JsonElement>

    @GET("./3/movie/popular")
    fun getTrending(@QueryMap option:Map<String, String>) : Single<JsonElement>

    @GET("./3/genre/movie/list")
    fun getGenre(@QueryMap option:Map<String, String>) : Single<WrapperGenre>

    @GET("./3/search/movie")
    fun getSearchMovie(@QueryMap option:Map<String, String>) : Single<JsonElement>

    @GET("/3/movie/{movie_id}?api_key="+BuildConfig.API_KEY)
    fun getDetailMovie(@Path("movie_id") movie_id:String) : Single<MovieDetail>
}