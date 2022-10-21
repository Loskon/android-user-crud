package com.loskon.usercrud.base.extension

import android.os.SystemClock
import android.view.View

fun View.setDebounceClickListener(
    debounceTime: Long = 600L,
    onClick: (View) -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(view: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                onClick(view)
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}