package com.edwinnurwansyah.movieretrofitrxkotlin.ui.main.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.request.RequestOptions
import android.opengl.ETC1.getHeight
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.edwinnurwansyah.movieretrofitrxkotlin.util.UIUtils


class InTheatreMovieViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_theatre_upcoming, parent, false)) {
    private val itemImageMovie = itemView.findViewById<ImageView>(R.id.itemImageMovie )

    fun bindTo(context: Context,movie: Movie){

        var requestOptions = RequestOptions().transforms(RoundedCorners(9))
        Glide.with(context).asBitmap()
            .load("http://image.tmdb.org/t/p/w500"+movie.poster_path)
            .apply(requestOptions)
            .into(UIUtils.getRoundedImageTarget(context, itemImageMovie, 20f))

//        itemView.layoutParams=RecyclerView.LayoutParams(width / 3, RecyclerView.LayoutParams.WRAP_CONTENT)

    }

}