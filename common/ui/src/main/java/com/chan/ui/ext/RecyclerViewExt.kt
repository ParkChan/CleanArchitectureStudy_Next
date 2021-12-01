package com.chan.ui.ext

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chan.ui.adapter.BaseAdapter

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bind_recyclerView_replaceItem")
fun <T> replaceItem(recyclerView: RecyclerView, items: List<T>?) {
    if (items == null) return

    @Suppress("UNCHECKED_CAST")
    (recyclerView.adapter as BaseAdapter<T>).run {
        replaceItems(items)
    }
}