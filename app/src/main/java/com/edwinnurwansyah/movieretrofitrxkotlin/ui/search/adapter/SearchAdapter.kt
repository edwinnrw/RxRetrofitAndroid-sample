package com.edwinnurwansyah.movieretrofitrxkotlin.ui.search.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Movie2


class SearchAdapter(private val items:List<Movie2>, val context: Context) : RecyclerView.Adapter<SearchViewHolder>(){
//    private  var items= mutableListOf<Movie2>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder {
        return SearchViewHolder(p0)
    }
//    fun addItem(items:List<Movie2>){
//        this.items.clear()
//        this.items.addAll(items)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        p0.bindTo(context,items[p1])
    }


}