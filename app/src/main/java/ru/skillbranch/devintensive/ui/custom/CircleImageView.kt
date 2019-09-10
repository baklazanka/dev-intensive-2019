
package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R
import java.lang.Exception
import android.graphics.Bitmap
import android.graphics.Shader
import android.view.View
import android.view.ViewOutlineProvider
import android.graphics.Outline
import android.graphics.BitmapShader
import android.graphics.ColorFilter

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object{
        private const val DEFAULT_BORDERCOLOR = Color.WHITE
        private const val DEFAULT_BORDERWIDTH = 2
    }

    private var borderColor = DEFAULT_BORDERCOLOR
    private var borderWidth = DEFAULT_BORDERWIDTH
    private var circleColor: Int = Color.BLACK
    private var cvColorFilter: ColorFilter? = null
    private val paint: Paint = Paint().apply { isAntiAlias = true }
    private val borderPaint: Paint = Paint().apply { isAntiAlias = true }
    private val backgroundPaint: Paint = Paint().apply { isAntiAlias = true }
    private var circleCenter = 0
    private var heightCircle: Int = 0
    private var cvBitmap: Bitmap? = null
    private var cvDrawable: Drawable? = null
    private var disableCircleTransformation: Boolean = false

    @Dimension fun getBorderWidth(): Int = borderWidth

    fun setBorderWidth(@Dimension(unit = Dimension.DP) dp: Int) {
        borderWidth = dp
        update()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        update()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId, context.theme)
        update()
    }

    fun isDisableCircleTransformation(): Boolean = disableCircleTransformation

    fun setDisableCircleTransformation(disabled: Boolean) {
        disableCircleTransformation = disabled
    }

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0)

            val borderWidthPixelSize = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH)
            borderWidth = (borderWidthPixelSize / resources.displayMetrics.density).toInt()
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDERCOLOR)
            a.recycle()
        }
    }

    override fun setColorFilter(cf: ColorFilter?) {
        super.setColorFilter(cf)
        cvColorFilter = cf
    }

    override fun getScaleType(): ScaleType {
        return super.getScaleType().let {
            if (it == null || it != ScaleType.CENTER_INSIDE){
                ScaleType.CENTER_CROP
            }
            else{
                it
            }
        }
    }

    override fun setScaleType(scaleType: ScaleType?) {
        require(!(scaleType != ScaleType.CENTER_CROP && scaleType != ScaleType.CENTER_INSIDE)) { String.format("ScaleType %s not supported. " +
            "Just ScaleType.CENTER_CROP & ScaleType.CENTER_INSIDE are available for this library.", scaleType) }
        super.setScaleType(scaleType)
    }

    override fun onDraw(canvas: Canvas) {
        if(disableCircleTransformation){
            super.onDraw(canvas)
            return
        }

        loadBitmap()

        if (cvBitmap == null){
            return
        }

        val circleCenterWithBorder = circleCenter + borderWidth.toFloat()

        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenterWithBorder, borderPaint)
        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenter.toFloat(), backgroundPaint)
        canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenter.toFloat(), paint)
    }

    private fun update(){
        if (cvBitmap != null){
            updateShader()
        }

        val usableWidth = width - (paddingLeft + paddingRight)
        val usableHeight = height - (paddingTop + paddingBottom)

        heightCircle = Math.min(usableWidth, usableHeight)

        circleCenter = (heightCircle - borderWidth * 2).toInt() / 2
        borderPaint.color = if (borderWidth == 0){
            circleColor
        }
        else{
            borderColor
        }

        manageElevation()
        invalidate()
    }

    private fun manageElevation() {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setOval(0, 0, heightCircle, heightCircle)
            }
        }
    }

    private fun loadBitmap() {
        if (cvDrawable == drawable){
            return
        }

        cvDrawable = drawable
        cvBitmap = drawableToBitmap(cvDrawable)
        updateShader()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        update()
    }

    private fun updateShader() {
        cvBitmap?.also {
            val shader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            val scale: Float
            val dx: Float
            val dy: Float

            when (scaleType) {
                ScaleType.CENTER_CROP -> if (it.width * height > width * it.height) {
                        scale = height / it.height.toFloat()
                        dx = (width - it.width * scale) * 0.5f
                        dy = 0f
                    }
                    else {
                        scale = width / it.width.toFloat()
                        dx = 0f
                        dy = (height - it.height * scale) * 0.5f
                    }
                ScaleType.CENTER_INSIDE -> if (it.width * height < width * it.height) {
                        scale = height / it.height.toFloat()
                        dx = (width - it.width * scale) * 0.5f
                        dy = 0f
                    }
                    else {
                        scale = width / it.width.toFloat()
                        dx = 0f
                        dy = (height - it.height * scale) * 0.5f
                    }
                else -> {
                    scale = 0f
                    dx = 0f
                    dy = 0f
                }
            }

            shader.setLocalMatrix(Matrix().apply {
                setScale(scale, scale)
                postTranslate(dx, dy)
            })

            paint.shader = shader

            paint.colorFilter = cvColorFilter
        }
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap? =
        when (drawable) {
            null -> null
            is BitmapDrawable -> drawable.bitmap
            else -> try {
                    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measure(widthMeasureSpec)
        val height = measure(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measure(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> specSize
            else -> heightCircle
        }
    }
 }
