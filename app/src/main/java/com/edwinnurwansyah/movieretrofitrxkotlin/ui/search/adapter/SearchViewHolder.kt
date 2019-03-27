package com.edwinnurwansyah.movieretrofitrxkotlin.ui.search.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie2
import com.edwinnurwansyah.movieretrofitrxkotlin.util.UIUtils
import com.github.ybq.android.spinkit.SpinKitView


class SearchViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie_search, parent, false)) {
    private val itemImageMovie = itemView.findViewById<ImageView>(R.id.imageViewMovie )
    private val itemTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
    private val itemGenre= itemView.findViewById<TextView>(R.id.textViewGenre)
    private val itemRuningTime= itemView.findViewById<TextView>(R.id.running_time)
    private val itemRating= itemView.findViewById<TextView>(R.id.rating)
    private val loader = itemView.findViewById<SpinKitView>(R.id.loader)
    var widht=0
    fun bindTo(context: Context,movie: Movie2){
        if (widht == 0)
            widht =  (parent.measuredWidth* .3).toInt()
        itemView.minimumWidth=widht

        var requestOptions = RequestOptions().transforms(RoundedCorners(9))
        Glide.with(context).asBitmap()
            .load("http://image.tmdb.org/t/p/w500"+movie.poster_path)
            .apply(requestOptions)
            .into(UIUtils.getRoundedImageTarget(context, itemImageMovie, 20f))
        itemTitle.text=movie.title
        itemRuningTime.text=movie.release_date
        movie.genre?.let {
            itemGenre.visibility= View.VISIBLE
            loader.visibility=View.GONE
            for (i in it.indices){

                if (i==it.size-1){

                    itemGenre.append(it[i].name)

                }else{
                    itemGenre.append(it[i].name+",")

                }

            }
        }

        itemRating.text=movie.vote_average

//        itemView.layoutParams=RecyclerView.LayoutParams(width / 3, RecyclerView.LayoutParams.WRAP_CONTENT)

    }

}