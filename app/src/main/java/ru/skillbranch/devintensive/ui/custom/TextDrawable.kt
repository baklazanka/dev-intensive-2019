package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import ru.skillbranch.devintensive.R

class TextDrawable constructor(
    context: Context,
    text: String
) : Drawable() {

    private var drawableText: String = ""
    private var backgroundColor: Int = Color.BLACK
    private val paint: Paint = Paint().apply { isAntiAlias = true }

    init {
        drawableText = text
        backgroundColor = context.resources.getColor(R.color.color_accent, context.theme)
    }

    override fun draw(canvas: Canvas) {
        val width: Int = bounds.width()
        val height: Int = bounds.height()
        val radius: Float = Math.min(width, height) / 2f
        val textSize: Float = Math.min(width, height) / 2f

        paint.color = backgroundColor

        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        paint.color = Color.WHITE
        paint.textSize = textSize
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true

        canvas.drawText(drawableText, width / 2f, height / 2f - (paint.descent() + paint.ascent()) / 2, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE
}