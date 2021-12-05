package com.chan.movie.ui.common.ext

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textInputAsFlow() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { textInput: CharSequence?, _, _, _ ->
        trySend(textInput)
    }
    awaitClose { this@textInputAsFlow.removeTextChangedListener(watcher) }
}