package com.edwinnurwansyah.movieretrofitrxkotlin.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.MovieSearch
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Status
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.search.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), TextWatcher {


    lateinit var viewModel:SearchViewModel
    lateinit var adapterSearch:SearchAdapter
    lateinit var movieList:MutableList<MovieSearch>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        movieList= mutableListOf()
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
//        viewModel.fetchComingSoon()
        editTextSearch.addTextChangedListener(this)
        adapterSearch= SearchAdapter(movieList,this)
        recyclerSearch.apply {
            layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
            adapter=adapterSearch
        }

        viewModel.getWrapperMovie().observe(this, Observer {
            it?.results.let {list->
                list?.let { it1 ->
                    movieList.clear()
                    movieList.addAll(it1)
                    adapterSearch.notifyDataSetChanged()
//                    adapterSearch.addItem(it1)
                }
            }

        })
        viewModel.movieData.observe(this, Observer {
            it?.let {movie->
                Log.d("TESGENREACTIVITY",movie.title)
                var position = -1
                for (i in movieList.indices){
                    if (movieList[i].id.contentEquals(movie.id)){
                        position=i
                    }
                }

                if (position == -1) {
                    // Ticket not found in the list
                    // This shouldn't happen
                    return@Observer
                }

                movieList[position] = movie

                adapterSearch.notifyItemChanged(position)
            }

        })
        viewModel.getProgressLoadStatus().observe(this, Observer {status->
            status?.let {
                when(it.status){
                    Status.SUCCESS->{
                        progress_circular.visibility= View.GONE
                        recyclerSearch.visibility= View.VISIBLE

                    }
                    Status.ERROR->{
                        Toast.makeText(this,it.error?.message, Toast.LENGTH_LONG)
                    }
                    Status.LOADING->{
                        progress_circular.visibility= View.VISIBLE
                        recyclerSearch.visibility =View.GONE

                    }
                }
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }
    override fun afterTextChanged(s: Editable?) {
        viewModel.fetchSearch(s.toString())

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}
