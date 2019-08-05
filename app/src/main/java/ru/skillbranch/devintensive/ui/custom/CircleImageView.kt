/*
package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import ru.skillbranch.devintensive.R
import java.lang.Exception
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.Shader
import android.R



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

    */
/*private val SCALE_TYPE = ScaleType.CENTER_CROP
    private var circleBackgroundColor = Color.TRANSPARENT

    private var drawableRect = RectF()
    private var borderRect = RectF()

    private val shaderMatrix = Matrix()
    private var bitmapPaint = Paint()
    private var borderPaint = Paint()
    private var circleBackgroundPaint = Paint()

    private var bitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null
    private var bitmapHeight: Int = 0
    private var bitmapWidth: Int = 0

    private var drawableRadius = 0f
    private var borderRadius = 0f
*//*


    private val DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000

    private val mBitmapShader: Shader? = null
    private val mShaderMatrix: Matrix? = null

    private val mBitmapDrawBounds: RectF? = null
    private val mStrokeBounds: RectF? = null

    private val mBitmap: Bitmap? = null

    private val mBitmapPaint: Paint? = null
    private val mStrokePaint: Paint? = null
    private val mPressedPaint: Paint? = null

    private val mInitialized: Boolean = false
    private val mPressed: Boolean = false
    private val mHighlightEnable: Boolean = false

*/
/*    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDERCOLOR)
            cv_borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH)
            //cv_borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH.toFloat())
            a.recycle()
        }

        super.setScaleType(SCALE_TYPE)
        setup()
    }*//*


*/
/*    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (bitmap == null){
            return
        }

        if (circleBackgroundColor != Color.TRANSPARENT){
            canvas?.drawCircle(drawableRect.centerX(), drawableRect.centerY(), drawableRadius, circleBackgroundPaint)
        }

        canvas?.drawCircle(drawableRect.centerX(), drawableRect.centerY(), drawableRadius, bitmapPaint)
        if (cv_borderWidth > 0) {
            canvas?.drawCircle(borderRect.centerX(), borderRect.centerY(), borderRadius, borderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    fun getBorderColor(): Int {
        return cv_borderColor
    }

    fun setBorderColor(hex: String) {
        cv_borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        //cv_borderColor = resources.getColor(colorId, context.theme)
        cv_borderColor = colorId
        borderPaint.color = cv_borderColor
        invalidate()
    }

    fun getCircleBackgroundColor(): Int{
        return circleBackgroundColor
    }

    fun setCircleBackgroundColor(@ColorRes colorId: Int){
        circleBackgroundColor = colorId
        circleBackgroundPaint.color = circleBackgroundColor
        invalidate()
    }

    fun setCircleBackgroundColorResource(@ColorRes colorId: Int){
        //setCircleBackgroundColor(context.resources.getColor(colorId))
        setCircleBackgroundColor(colorId)
    }

    @Dimension fun getBorderWidth(): Int {
        return cv_borderWidth
    }

    //fun setBorderWidth(@Dimension(unit = Dimension.DP) dp: Int) {
    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = dp
        setup()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null){
            return null
        }

        if (drawable is BitmapDrawable){
            return (drawable).bitmap
        }

        try {
            var _bitmap: Bitmap
            if (drawable is ColorDrawable){
                _bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
            }
            else{
                _bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            }

            var canvas: Canvas = Canvas(_bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return _bitmap
        }
        catch (e: Exception){
            return null
        }
    }

    private fun initializeBitmap() {
        bitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    private fun setup(){
        if (width == 0 && height == 0){
            return
        }

        if (bitmap == null){
            invalidate()
            return
        }

        bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        bitmapPaint.isAntiAlias = true
        bitmapPaint.shader = bitmapShader

        borderPaint.style = Paint.Style.STROKE
        borderPaint.isAntiAlias = true
        borderPaint.color = cv_borderColor
        borderPaint.strokeWidth = cv_borderWidth.toFloat()

        circleBackgroundPaint.style = Paint.Style.FILL
        circleBackgroundPaint.isAntiAlias = true
        circleBackgroundPaint.color = circleBackgroundColor

        bitmapHeight = bitmap!!.height
        bitmapWidth = bitmap!!.width

        borderRect.set(calculateBounds())
        borderRadius = Math.min((borderRect.height() - cv_borderWidth) / 2.0f, (borderRect.width() - cv_borderWidth) / 2.0f)

        drawableRect.set(borderRect)
        drawableRect.inset(cv_borderWidth - 1.0f, cv_borderWidth - 1.0f)
        drawableRadius = Math.min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f)

        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom

        val sideLength = Math.min(availableWidth, availableHeight)

        val leftSize = paddingLeft + (availableWidth - sideLength) / 2f
        val topSize = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(leftSize, topSize, leftSize + sideLength, topSize + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        shaderMatrix.set(null)

        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / bitmapHeight as Float
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = drawableRect.width() / bitmapWidth as Float
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }

        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate((dx + 0.5f).toInt() + drawableRect.left, (dy + 0.5f).toInt() + drawableRect.top)

        bitmapShader?.setLocalMatrix(shaderMatrix)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return
    }

    private fun inTouchableArea(x: Float, y: Float){
        return Math.pow(x - borderRect.centerX(), 2) + Math.pow(y - borderRect.centerY(), 2)
    }*/// //////////////////////
//
// init {
// if (attrs != null){
// val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
// cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDERCOLOR)
// cv_borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH)
// //cv_borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDERWIDTH.toFloat())
// a.recycle()
// }
//
// shaderMatrix = Matrix()
// bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
// borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
// mStrokeBounds = RectF()
// mBitmapDrawBounds = RectF()
// borderPaint.setColor(cv_borderColor)
// mStrokePaint.setStyle(Paint.Style.STROKE)
// borderPaint.setStrokeWidth(cv_borderWidth.toFloat())
//
// mPressedPaint = new Paint(Paint.ANTI_ALIAS_FLAG)
// mPressedPaint.setColor(highlightColor)
// mPressedPaint.setStyle(Paint.Style.FILL)
//
// mHighlightEnable = highlightEnable
// mInitialized = true
//
// setupBitmap()
// }
//
// override fun setImageResource(@DrawableRes resId: Int) {
// super.setImageResource(resId)
// setupBitmap()
// }
//
// override fun setImageDrawable(drawable: Drawable?) {
// super.setImageDrawable(drawable)
// setupBitmap()
// }
//
// override fun setImageBitmap(bm: Bitmap?) {
// super.setImageBitmap(bm)
// setupBitmap()
// }
//
// override fun setImageURI(uri: Uri?) {
// super.setImageURI(uri)
// setupBitmap()
// }
//
// override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
// super.onSizeChanged(w, h, oldw, oldh)
//
// float halfStrokeWidth = mStrokePaint.getStrokeWidth() / 2f
// updateCircleDrawBounds(mBitmapDrawBounds)
// mStrokeBounds.set(mBitmapDrawBounds)
// mStrokeBounds.inset(halfStrokeWidth, halfStrokeWidth)
//
// updateBitmapSize();
// }
//
// override fun onTouchEvent(event: MotionEvent?): Boolean {
// boolean processed = false
// switch (event.getAction()) {
// case MotionEvent.ACTION_DOWN:
// if (!isInCircle(event.getX(), event.getY())) {
// return false
// }
// processed = true
// mPressed = true
// invalidate()
// break
// case MotionEvent.ACTION_CANCEL:
// case MotionEvent.ACTION_UP:
// processed = true
// mPressed = false
// invalidate()
// if (!isInCircle(event.getX(), event.getY())) {
// return false
// }
// break
// }
// return super.onTouchEvent(event) || processed
// }
//
// override fun onDraw(canvas: Canvas?) {
// drawBitmap(canvas)
// drawStroke(canvas)
// drawHighlight(canvas)
// }
//
// fun isHighlightEnable(): Boolean {
// return mHighlightEnable
// }
//
// fun setHighlightEnable(enable: Boolean) {
// mHighlightEnable = enable
// invalidate()
// }
//
// @ColorInt
// fun getHighlightColor(): Int {
// return mPressedPaint.getColor()
// }
//
// fun setHighlightColor(@ColorInt color: Int) {
// mPressedPaint.setColor(color)
// invalidate()
// }
//
// @ColorInt
// fun getStrokeColor(): Int {
// return mStrokePaint.getColor()
// }
//
// fun setStrokeColor(@ColorInt color: Int) {
// mStrokePaint.setColor(color)
// invalidate()
// }
//
// @Dimension
// fun getStrokeWidth(): Float {
// return mStrokePaint.getStrokeWidth()
// }
//
// fun setStrokeWidth(@Dimension width: Float) {
// mStrokePaint.setStrokeWidth(width)
// invalidate()
// }
//
// fun drawHighlight(canvas: Canvas) {
// if (mHighlightEnable && mPressed) {
// canvas.drawOval(mBitmapDrawBounds, mPressedPaint)
// }
// }
//
// fun drawStroke(canvas: Canvas) {
// if (mStrokePaint.getStrokeWidth() > 0f) {
// canvas.drawOval(mStrokeBounds, mStrokePaint)
// }
// }
//
// fun drawBitmap(canvas: Canvas) {
// canvas.drawOval(mBitmapDrawBounds, mBitmapPaint)
// }
//
// fun updateCircleDrawBounds(bounds: RectF) {
// float contentWidth = getWidth() - getPaddingLeft() - getPaddingRight()
// float contentHeight = getHeight() - getPaddingTop() - getPaddingBottom()
//
// var left: Float = getPaddingLeft()
// float top = getPaddingTop()
// if (contentWidth > contentHeight) {
// left += (contentWidth - contentHeight) / 2f
// }
// else {
// top += (contentHeight - contentWidth) / 2f
// }
//
// float diameter = Math.min(contentWidth, contentHeight)
// bounds.set(left, top, left + diameter, top + diameter)
// }
//
// fun setupBitmap() {
// if (!mInitialized) {
// return
// }
// mBitmap = getBitmapFromDrawable(getDrawable())
// if (mBitmap == null) {
// return
// }
//
// mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
// mBitmapPaint.setShader(mBitmapShader)
//
// updateBitmapSize()
// }
//
// fun updateBitmapSize() {
// if (mBitmap == null) {
// return
// }
//
// var dx: Float
// var dy: Float
// var scale: Float
//
// if (mBitmap.width < mBitmap.getHeight()) {
// scale = mBitmapDrawBounds.width() / (float)mBitmap.getWidth()
// dx = mBitmapDrawBounds.left
// dy = mBitmapDrawBounds.top - (mBitmap.getHeight() * scale / 2f) + (mBitmapDrawBounds.width() / 2f)
// }
// else {
// scale = mBitmapDrawBounds.height() / (float)mBitmap.getHeight()
// dx = mBitmapDrawBounds.left - (mBitmap.getWidth() * scale / 2f) + (mBitmapDrawBounds.width() / 2f)
// dy = mBitmapDrawBounds.top
// }
// mShaderMatrix.setScale(scale, scale)
// mShaderMatrix.postTranslate(dx, dy)
// mBitmapShader.setLocalMatrix(mShaderMatrix)
// }
//
// fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
// if (drawable == null) {
// return null
// }
//
// if (drawable is BitmapDrawable) {
// return drawable.bitmap
// }
//
// val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
// val canvas = Canvas(bitmap)
// drawable.setBounds(0, 0, canvas.width, canvas.height)
// drawable.draw(canvas)
//
// return bitmap
// }
//
// fun isInCircle(x: Float, y: Float): Boolean {
// val distance = Math.sqrt(Math.pow(mBitmapDrawBounds.centerX() - x, 2) + Math.pow(mBitmapDrawBounds.centerY() - y, 2)
// )
// return distance <= (mBitmapDrawBounds.width() / 2)
// }
// }
//
//
//
//
// //(https://)?(www.)?github.com/(\w*(-)?\w{2,}[^/])    регулярка для последнего
// }
