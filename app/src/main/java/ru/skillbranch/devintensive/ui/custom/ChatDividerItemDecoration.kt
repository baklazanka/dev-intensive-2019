package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R

class ChatDividerItemDecoration constructor(
    context: Context
) : RecyclerView.ItemDecoration() {

    private val attrs = intArrayOf(android.R.attr.listDivider)
    private var avatarSize: Float = 0f
    private var mDivider: Drawable? = null

    init {
        val a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)
        avatarSize = context.resources.getDimension(R.dimen.avatar_item_size)

        a.recycle()
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = parent.paddingLeft + avatarSize.toInt() + child.paddingLeft + child.paddingRight
            val top = child.bottom + params.bottomMargin
            val bottom = top + (mDivider?.intrinsicHeight ?: 0)

            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
    }
}