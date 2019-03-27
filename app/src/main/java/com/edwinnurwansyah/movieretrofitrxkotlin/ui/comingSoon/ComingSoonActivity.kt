package com.edwinnurwansyah.movieretrofitrxkotlin.ui.comingSoon

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
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.comingSoon.adapter.ComingSoonAdapter
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.nowWatching.NowWatchingViewModel
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.nowWatching.adapter.NowWatchingAdapter
import kotlinx.android.synthetic.main.activity_coming_soon.*
import kotlinx.android.synthetic.main.toolbar.*

class ComingSoonActivity : AppCompatActivity() {
    lateinit var viewModel: CommingSoonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = "Coming Soon"
        viewModel = ViewModelProviders.of(this).get(CommingSoonViewModel::class.java)
        val adapter= ComingSoonAdapter(this)
        recyclerViewComingSoon.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        viewModel.fetchComingSoon()
        viewModel.itemPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.getProgressLoadStatus().observe(this, Observer {status->
            status?.let {
                when(it.status){
                    Status.SUCCESS->{
                        progress_circular.visibility= View.GONE
                        recyclerViewComingSoon.visibility= View.VISIBLE
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
        recyclerViewComingSoon.adapter=adapter
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
