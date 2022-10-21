package com.loskon.usercrud.base.extension

import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.widget.EditText

fun EditText.setDisabledSpaceFilter() {
    filters = arrayOf(InputFilter { source, start, end, _, _, _ ->
        val srcChangeLength = end - start
        if (srcChangeLength <= 0) return@InputFilter source

        val result = if (source is SpannableStringBuilder) source else SpannableStringBuilder(source)
        for (i in end - 1 downTo start) if (Character.isSpaceChar(result[i])) result.delete(i, i+1)

        return@InputFilter result
    })
}