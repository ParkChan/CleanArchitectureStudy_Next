package com.chan.ui.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * https://gist.github.com/JoseAlcerreca/5b661f1800e1e654f07cc54fe87441af#file-event-kt
 */
open class Event<T>(value: T) {
    var value = value
        private set

    private var isAlreadyHandled = false

    fun isActive(): Boolean = if (isAlreadyHandled) {
        false
    } else {
        isAlreadyHandled = true
        true
    }
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: Observer<T>) =
    observe(owner) {
        if (it.isActive()) {
            observer.onChanged(it.value)
        }
    }
