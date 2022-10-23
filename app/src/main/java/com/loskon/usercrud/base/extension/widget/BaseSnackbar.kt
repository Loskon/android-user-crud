package com.loskon.usercrud.base.extension.widget

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

open class BaseSnackbar {

    private var snackbar: Snackbar? = null

    fun make(view: View, message: String, length: Int = Snackbar.LENGTH_LONG): BaseSnackbar {
        snackbar = Snackbar.make(view, message, length)
        return this
    }

    fun setAnchorView(anchorView: View): BaseSnackbar {
        snackbar?.anchorView = anchorView
        return this
    }

    fun setTopGravity() {
        val view = snackbar?.view
        val params = view?.layoutParams

        when (params) {
            is CoordinatorLayout.LayoutParams -> params.gravity = Gravity.TOP
            is FrameLayout.LayoutParams -> params.gravity = Gravity.TOP
            is ConstraintLayout.LayoutParams -> params.topToTop = view.id
            else -> return
        }

        view.layoutParams = params
    }

    fun setBackgroundTintList(color: Int) {
        snackbar?.view?.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun enableHideByClickSnackbar() {
        snackbar?.view?.setOnClickListener { dismiss() }
    }

    fun show() = snackbar?.show()

    fun dismiss() = snackbar?.dismiss()

    inline fun create(functions: BaseSnackbar.() -> Unit): BaseSnackbar {
        this.functions()
        return this
    }
}