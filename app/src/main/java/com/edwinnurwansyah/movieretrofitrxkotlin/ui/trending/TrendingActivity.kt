package com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Status
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.activity_trending.*
import kotlinx.android.synthetic.main.toolbar.*

class TrendingActivity : AppCompatActivity() {

    lateinit var viewModel: TrendingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = "Trending Movies"
        viewModel = ViewModelProviders.of(this).get(TrendingViewModel::class.java)
        val adapter=TrendingAdapter(this)
        recyclerViewTrending.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        viewModel.fetchTrending()
        viewModel.itemPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.getProgressLoadStatus().observe(this, Observer {status->
            status?.let {
                when(it.status){
                    Status.SUCCESS->{
                        progress_circular.visibility= View.GONE
                        recyclerViewTrending.visibility=View.VISIBLE
                    }
                    Status.ERROR->{
                        Toast.makeText(this,it.error?.message,Toast.LENGTH_LONG)
                    }
                    Status.LOADING->{
                        progress_circular.visibility= View.VISIBLE

                    }
                }
            }

        })
//        viewModel.getTrendingWithGenre().observe(this, Observer {
//            adapter.submitList(it?.value)
//        })
//        viewModel.getTrendingWithGenre().observe(this, Observer(adapter::submitList))
        recyclerViewTrending.adapter=adapter


    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
