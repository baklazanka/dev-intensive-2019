package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_profile.*

fun Activity.hideKeyboard() {
    val view: View? = this.currentFocus
    if (view != null) {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView: View = this.window.decorView

    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)

    val rootViewHeight: Int = rootView.height
    val keyboardHeight: Int = rootViewHeight - rect.bottom

    return (keyboardHeight > rootViewHeight * 0.15)
}

fun Activity.isKeyboardClosed(): Boolean {
    return (!this.isKeyboardOpen())
}

