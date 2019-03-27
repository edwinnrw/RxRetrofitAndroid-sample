package com.edwinnurwansyah.movieretrofitrxkotlin.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.edwinnurwansyah.movieretrofitrxkotlin.R
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.comingSoon.ComingSoonActivity
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.main.adapter.InTheatreMovieAdapter
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.main.adapter.TrendingMovieAdapter
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.main.adapter.UpcomingMovieAdapter
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.nowWatching.NowWatchingActivity
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.search.SearchActivity
import com.edwinnurwansyah.movieretrofitrxkotlin.ui.trending.TrendingActivity
import com.edwinnurwansyah.movieretrofitrxkotlin.util.PeekingLiniearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Flowable
import android.util.Log
import java.util.concurrent.TimeUnit
import io.reactivex.Observable


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = "MVX"

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        toggle.syncState()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val adapter=TrendingMovieAdapter(this)
        recyclerViewTrending.layoutManager=PeekingLiniearLayoutManager(this,0.8f,LinearLayoutManager.HORIZONTAL ,false)
        viewModel.fetchTrending().observe(this, Observer(adapter::submitList))
        recyclerViewTrending.adapter=adapter

        val adapterTheatre=InTheatreMovieAdapter(this)
        recyclerViewTheatre.layoutManager=PeekingLiniearLayoutManager(this,0.38f ,LinearLayoutManager.HORIZONTAL ,false)
        viewModel.fetchMovieNow().observe(this,Observer(adapterTheatre::submitList))
        recyclerViewTheatre.adapter=adapterTheatre

        val adapterUpcoming=UpcomingMovieAdapter(this)
        recyclerViewUpcoming.layoutManager=PeekingLiniearLayoutManager(this,0.38f ,LinearLayoutManager.HORIZONTAL ,false)
        viewModel.fetchMovieUpcoming().observe(this,Observer(adapterUpcoming::submitList))
        recyclerViewUpcoming.adapter=adapterUpcoming

        textViewAllTrending.setOnClickListener {
            var intent=Intent(this,TrendingActivity::class.java)
            startActivity(intent)
        }
        textViewAllTheatre.setOnClickListener {
            var intent=Intent(this,NowWatchingActivity::class.java)
            startActivity(intent)
        }
        textViewAllUpcoming.setOnClickListener {
            var intent=Intent(this,ComingSoonActivity::class.java)
            startActivity(intent)
        }
        editTextSearch.setOnClickListener {
            var intent=Intent(this,SearchActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
//

//
//        val displayMetrics = resources.displayMetrics
//        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
//        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
//
//        Log.d("debug", dpWidth.toString()+"x"+ dpHeight.toString())

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
