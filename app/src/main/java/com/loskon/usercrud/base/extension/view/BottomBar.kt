package com.loskon.usercrud.base.extension.view

import android.os.SystemClock
import android.view.MenuItem
import com.google.android.material.bottomappbar.BottomAppBar

fun BottomAppBar.setDebounceMenuItemClickListener(
    menuItemId: Int,
    debounceTime: Long = 600L,
    onMenuItemClick: (item: MenuItem) -> Unit
) {
    menu.findItem(menuItemId).setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
        private var lastClickTime: Long = 0

        override fun onMenuItemClick(item: MenuItem): Boolean {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return false
            } else {
                onMenuItemClick(item)
            }

            lastClickTime = SystemClock.elapsedRealtime()
            return true
        }
    })
}

fun BottomAppBar.setMenuItemVisibility(menuItemId: Int, visible: Boolean) {
    menu.findItem(menuItemId).isVisible = visible
}