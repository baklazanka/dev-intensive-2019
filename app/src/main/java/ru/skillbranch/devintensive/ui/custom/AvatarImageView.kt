package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.*

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) { // : ImageView(context, attrs, defStyleAttr) {

    companion object{
        private const val DEFAULT_BORDER_WIDTH = 1
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_SIZE = 40
    }

    @Px
    var borderWidth: Float = dpToPx(DEFAULT_BORDER_WIDTH)
    @ColorInt
    private var borderColor: Int = Color.WHITE
    private var initials: String = "??"

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val initialsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private val borderRect = Rect()
    private var size = 0

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView)

            borderWidth = a.getDimension(
                R.styleable.AvatarImageView_aiv_borderWidth,
                dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = a.getColor(R.styleable.AvatarImageView_aiv_borderColor, DEFAULT_BORDER_COLOR)
            a.recycle()
        }

        scaleType = ScaleType.CENTER_CROP
        setup()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(max(initSize, size), max(initSize, size))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0) return

        with(viewRect){
            left = 0
            top = 0
            right = w
            bottom = h
        }

        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        if (drawable != null) drawAvatar(canvas)
        else drawInitials(canvas)

        //resize rect
        val half = (borderWidth / 2).toInt()
        borderRect.set(viewRect)
        borderRect.inset(half, half)
        canvas.drawOval(borderRect.toRectF(), borderPaint)
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        prepareShader(width, height)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        prepareShader(width, height)
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        prepareShader(width, height)
    }

    fun setInitials(initials: String) {
        this.initials = initials
        invalidate()
    }

    fun setBorderWidth(@Dimension width: Int) {
        borderWidth = dpToPx(width)
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    fun setBorderColor(@ColorInt color: Int) {
        borderColor = color
        borderPaint.color = borderColor
        invalidate()
    }

    private fun setup() {
        with(borderPaint){
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    private fun prepareShader(w: Int, h: Int) {
        if (w == 0 || drawable == null) return
        val srcBm = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
        avatarPaint.shader = BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when(MeasureSpec.getMode(spec)){
            MeasureSpec.UNSPECIFIED -> dpToPx(DEFAULT_SIZE).toInt() // resolveDefaultSize
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec) // from spec
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec) // from spec
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun drawAvatar(canvas: Canvas) {
        canvas.drawOval(viewRect.toRectF(), avatarPaint)
    }

    private fun drawInitials(canvas: Canvas) {
        Utils.drawInitials(canvas, initials, initialsPaint, viewRect)
    }

    private fun dpToPx(dp: Int): Float {
        return dp.toFloat() * context.resources.displayMetrics.density
    }
}