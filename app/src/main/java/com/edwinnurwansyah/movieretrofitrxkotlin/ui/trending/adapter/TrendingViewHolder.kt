package com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.edwinnurwansyah.movieretrofitrxkotlin.util.UIUtils


class TrendingViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)) {
    private val itemImageMovie = itemView.findViewById<ImageView>(R.id.imageViewMovie )
    private val itemTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
    private val itemGenre= itemView.findViewById<TextView>(R.id.textViewGenre)
    private val itemRuningTime= itemView.findViewById<TextView>(R.id.running_time)
    private val itemRating= itemView.findViewById<TextView>(R.id.rating)
    var widht=0
    fun bindTo(context: Context,movie: Movie){
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
        movie.genre_ids?.let {
            for (i in it.indices){
                if (i==it.size-1){
                    itemGenre.append(it[i])

                }else{
                    itemGenre.append(it[i]+",")

                }

            }
        }

        itemRating.text=movie.vote_average

//        itemView.layoutParams=RecyclerView.LayoutParams(width / 3, RecyclerView.LayoutParams.WRAP_CONTENT)

    }

}