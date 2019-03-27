package com.edwinnurwansyah.movieretrofitrxkotlin.ui.main.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie


class UpcomingMovieAdapter(val context: Context): PagedListAdapter<Movie, UpcomingMovieViewHolder>(diffCallback){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UpcomingMovieViewHolder {

       return UpcomingMovieViewHolder(p0)

    }

    override fun onBindViewHolder(p0: UpcomingMovieViewHolder, p1: Int) {
        getItem(p1)?.let { p0.bindTo(context, it) }
    }


    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}