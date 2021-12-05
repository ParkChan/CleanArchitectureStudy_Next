package com.chan.movie.ui.common.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chan.movie.R

@BindingAdapter("setGlideThumb")
fun bindingGlideThumb(imageView: ImageView, uri: String?) {
    Glide.with(imageView)
        .load(uri)
        .error(R.drawable.ic_bg_error)
        .fallback(R.drawable.ic_bg_error)
        .into(imageView)
}