
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
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ImageView(context, attrs, defStyleAttr) {

    companion object{
        private const val DEFAULT_BORDERCOLOR = Color.WHITE
        private const val DEFAULT_BORDERWIDTH = 2
    }

    private var cv_borderColor = DEFAULT_BORDERCOLOR
    private var cv_borderWidth = DEFAULT_BORDERWIDTH

    private var circleColor: Int = Color.BLACK
    private var cv_colorFilter: ColorFilter? = null

    private val paint: Paint = Paint().apply { isAntiAlias = true }
    private val borderPaint: Paint = Paint().apply { isAntiAlias = true }
    private val backgroundPaint: Paint = Paint().apply { isAntiAlias = true }

    private var circleCenter = 0
    private var heightCircle: Int = 0

    private var cv_bitmap: Bitmap? = null
    private var cv_drawable: Drawable? = null

//    fun getCircleColor(): Int = circleColor
//
//    fun setCircleColor(@ColorRes colorId: Int){
//        circleCenter = colorId
//        backgroundPaint.color = circleColor
//        invalidate()
//    }

    @Dimension fun getBorderWidth(): Int = cv_borderWidth

    //fun setBorderWidth(@Dimension(unit = Dimension.DP) dp: Int) {
    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = dp
        update()
    }

    fun getBorderColor(): Int = cv_borderColor

    fun setBorderColor(hex: String) {
        cv_borderColor = Color.parseColor(hex)
        update()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        //borderColor = resources.getColor(colorId, context.theme)
        cv_borderColor = colorId
        //borderPaint.color = borderColor
        update()
    }

//    fun getCVColorFilter(): ColorFilter? = cv_colorFilter
//
//    fun setCVColorFilter(cf: ColorFilter) {
//        if (cv_colorFilter != cf) {
//            cv_colorFilter = cf
//            if (cv_colorFilter != null) {
//                cv_drawable = null
//                invalidate()
//            }
//        }
//    }

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDERCOLOR)
            cv_borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH)
            //cv_borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH.toFloat())
            a.recycle()
        }
    }

    override fun setColorFilter(cf: ColorFilter?) {
        super.setColorFilter(cf)
        cv_colorFilter = cf
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
        if (scaleType != ScaleType.CENTER_CROP && scaleType != ScaleType.CENTER_INSIDE) {
            throw IllegalArgumentException(String.format("ScaleType %s not supported. " +
                    "Just ScaleType.CENTER_CROP & ScaleType.CENTER_INSIDE are available for this library.", scaleType))
        } else {
            super.setScaleType(scaleType)
        }
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        loadBitmap()

        if (cv_bitmap == null){
            return
        }

        val circleCenterWithborder = circleCenter.toFloat() + cv_borderWidth.toFloat()

        canvas.drawCircle(circleCenterWithborder, circleCenterWithborder, circleCenterWithborder, borderPaint)
        canvas.drawCircle(circleCenterWithborder, circleCenterWithborder, circleCenter.toFloat(), backgroundPaint)
        canvas.drawCircle(circleCenterWithborder, circleCenterWithborder, circleCenter.toFloat(), paint)
    }

    private fun update(){
        if (cv_bitmap != null){
            updateShader()
        }

        val usableWidth = width - (paddingLeft + paddingRight)
        val usableHeight = height - (paddingTop + paddingBottom)

        heightCircle = Math.min(usableWidth, usableHeight)

        circleCenter = (heightCircle - cv_borderWidth * 2) / 2
        borderPaint.color = if (cv_borderWidth == 0){
            circleColor
        }
        else{
            cv_borderColor
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
        if (cv_drawable == drawable){
            return
        }

        cv_drawable = drawable
        cv_bitmap = drawableToBitmap(cv_drawable)
        updateShader()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        update()
    }

    private fun updateShader() {
        cv_bitmap?.also {
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

            paint.colorFilter = cv_colorFilter
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
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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

 //(https://)?(www.)?github.com/(\w*(-)?\w{2,}[^/])    регулярка для последнего
 }
