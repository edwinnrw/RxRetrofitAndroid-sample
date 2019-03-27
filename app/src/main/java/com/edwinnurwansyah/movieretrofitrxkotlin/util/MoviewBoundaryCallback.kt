package com.edwinnurwansyah.movieretrofitrxkotlin.util

import android.arch.paging.PagedList
import com.edwinnurwansyah.movieretrofitrxkotlin.model.WrapperMovie
import java.util.concurrent.Executors

class MoviewBoundaryCallback :
    PagedList.BoundaryCallback<WrapperMovie>() {
    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
//        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
//
//        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: WrapperMovie) {
        super.onItemAtEndLoaded(itemAtEnd)
    }
}