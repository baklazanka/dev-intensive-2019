package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import ru.skillbranch.devintensive.utils.Utils

class AvatarChip constructor(
    context: Context
) : Chip(context) {

    fun setIconUrl(avatar: String?, initials: String): AvatarChip {
        Glide.with(this)
            .load(avatar)
            .circleCrop()
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    chipIcon = placeholder
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    //chipIcon = resources.getDrawable(R.drawable.avatar_default, context.theme)
                    chipIcon = drawInitials(initials)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    chipIcon = resource
                }
            })

        return this
    }

    private fun drawInitials(initials: String): Drawable {
        val initialsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val viewRect = Rect()
        with(viewRect){
            left = 0
            top = 0
            right = 24
            bottom = 24
        }

        val bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        Utils.drawInitials(canvas, initials, initialsPaint, viewRect)

        return bitmap.toDrawable(resources)
    }
}