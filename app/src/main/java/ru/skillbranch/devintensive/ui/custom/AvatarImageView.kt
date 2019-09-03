package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import ru.skillbranch.devintensive.R

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private var avatarSize: Int = 0
    private var rect: Rect = Rect()
    private var pathR: Path = Path()
    private lateinit var paintText: Paint
    private lateinit var paintBorder: Paint
    private var borderWidth: Float = DEFAULT_BORDER_WIDTH
    private var borderColor: Int = DEFAULT_BORDER_COLOR
    private var initials: String? = null
    private val bgColors = arrayOf(
        "#7BC862",
        "#E17076",
        "#FAA774",
        "#6EC9CB",
        "#65AADD",
        "#A695E7",
        "#EE7AAE"
    )

    companion object{
        private const val DEFAULT_BORDER_WIDTH = 2f
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView, defStyleAttr, 0)
            borderWidth = a.getDimension(R.styleable.AvatarImageView_aiv_borderWidth, DEFAULT_BORDER_WIDTH)
            borderColor = a.getColor(R.styleable.AvatarImageView_aiv_borderColor, DEFAULT_BORDER_COLOR)
            a.recycle()
        }
    }

    fun getBorderWidth(): Float = borderWidth

    fun setBorderWidth(dp: Float) {
        borderWidth = dp
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId, context.theme)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val radius: Float = Math.min(canvas!!.width, canvas.height) / 2f

        paintBorder.color = Color.parseColor(bgColors[0])
        //canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenterWithBorder, paintBorder)
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), radius, paintBorder)

        //canvas.drawCircle(circleCenterWithBorder, circleCenterWithBorder, circleCenter.toFloat(), paintBorder)

        paintText.color = Color.WHITE
        paintText.textSize = radius
        paintText.style = Paint.Style.FILL
        paintText.textAlign = Paint.Align.CENTER
        paintText.isFakeBoldText = true

        canvas.drawText("??", canvas.width / 2f, canvas.height / 2f - (paintText.descent() + paintText.ascent()) / 2, paintText)
    }

    fun setInitials(initials: String) {
        this.initials = initials
    }
}