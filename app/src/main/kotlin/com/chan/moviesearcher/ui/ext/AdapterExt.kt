package com.chan.moviesearcher.ui.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chan.moviesearcher.R


@BindingAdapter("setGlideThumb")
fun bindingGlideThumb(imageView: ImageView, uri: String?) {
    if (uri.isNullOrBlank()) return

    Glide.with(imageView)
        .load(uri)
        .error(R.drawable.ic_launcher_background)
        .into(imageView)
}