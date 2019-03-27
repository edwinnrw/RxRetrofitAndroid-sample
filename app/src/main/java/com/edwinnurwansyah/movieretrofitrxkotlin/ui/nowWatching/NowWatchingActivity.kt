package com.edwinnurwansyah.movieretrofitrxkotlin.ui.nowWatching

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.model.Status
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.nowWatching.adapter.NowWatchingAdapter
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.activity_now_watching.*
import kotlinx.android.synthetic.main.toolbar.*

class NowWatchingActivity : AppCompatActivity() {
    lateinit var viewModel: NowWatchingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_watching)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = "Now Watching"
        viewModel = ViewModelProviders.of(this).get(NowWatchingViewModel::class.java)
        val adapter= NowWatchingAdapter(this)
        recyclerViewNow.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        viewModel.fetchNowWatching()
        viewModel.itemPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.getProgressLoadStatus().observe(this, Observer {status->
            status?.let {
                Log.d("TesResponse",it.status.toString())
                when(it.status){
                    Status.SUCCESS->{
                        progress_circular.visibility= View.GONE
                        recyclerViewNow.visibility= View.VISIBLE
                    }
                    Status.ERROR->{
                        Toast.makeText(this,it.error?.message, Toast.LENGTH_LONG)
                    }
                    Status.LOADING->{
                        progress_circular.visibility= View.VISIBLE

                    }
                }
            }

        })
        recyclerViewNow.adapter=adapter
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
