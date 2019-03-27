package com.edwinnurwansyah.movieretrofitrxkotlin.ui.search.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.edwinnurwansyah.movieretrofitrxkotlin.model.MovieSearch


class SearchAdapter(private val items:List<MovieSearch>, val context: Context) : RecyclerView.Adapter<SearchViewHolder>(){
//    private  var items= mutableListOf<MovieSearch>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder {
        return SearchViewHolder(p0)
    }
//    fun addItem(items:List<MovieSearch>){
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