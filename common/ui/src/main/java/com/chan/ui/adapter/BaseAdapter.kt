package com.chan.ui.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(
        @LayoutRes private val layoutResourceId: Int,
        private val viewHolderBindingId: Int,
        private val viewModel: Map<Int, ViewModel>
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val itemList = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
            BaseViewHolder(parent, layoutResourceId, viewHolderBindingId, viewModel)

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(item = itemList[position])
    }

    fun replaceItems(item: List<T>) {
        itemList.run {
            clear()
            addAll(item)
        }
    }
}
