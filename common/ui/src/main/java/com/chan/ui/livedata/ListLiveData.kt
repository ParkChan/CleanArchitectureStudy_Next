package com.chan.ui.livedata

import androidx.lifecycle.MutableLiveData


class ListLiveData<T> : MutableLiveData<MutableList<T>>() {

    init {
        value = mutableListOf()
    }

    fun add(item: T) {
        val items: MutableList<T>? = value
        items?.add(item)
        value = items
    }

    fun addAll(list: List<T>) {
        val items: MutableList<T>? = value
        items?.addAll(list)
        value = items
    }

    fun clear(notify: Boolean) {
        val items: MutableList<T>? = value
        items?.clear()
        if (notify) {
            value = items
        }
    }

    fun remove(item: T) {
        val items: MutableList<T>? = value
        items?.remove(item)
        value = items
    }

    fun notifyChange() {
        val items: MutableList<T>? = value
        value = items
    }


}