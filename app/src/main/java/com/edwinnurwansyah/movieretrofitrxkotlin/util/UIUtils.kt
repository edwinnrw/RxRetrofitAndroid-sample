package com.edwinnurwansyah.movieretrofitrxkotlin.util

import android.content.Context
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.request.target.BitmapImageViewTarget



object UIUtils{
    fun getRoundedImageTarget(
        context: Context, imageView: ImageView,
        radius: Float
    ): BitmapImageViewTarget {
        return object : BitmapImageViewTarget(imageView) {
            override fun setResource(resource: Bitmap?) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
                circularBitmapDrawable.cornerRadius = radius
                imageView.setImageDrawable(circularBitmapDrawable)
            }
        }
    }
}