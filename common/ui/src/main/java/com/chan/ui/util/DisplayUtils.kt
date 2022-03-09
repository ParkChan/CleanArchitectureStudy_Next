package com.chan.ui.util

import android.content.Context
import kotlin.math.roundToInt

fun convertDpToPx(context: Context, dp: Int): Int =
    (dp * context.resources.displayMetrics.density).roundToInt()

