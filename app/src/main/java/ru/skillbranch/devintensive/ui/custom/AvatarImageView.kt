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
//    private var rect: Rect = Rect()
    private var rect: RectF = RectF()
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


    // Ниже уже своя реализация


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

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId, context.theme)
    }

    override fun onDraw(canvas: Canvas?) {
        val radius = height / 2f

        with(rect){
            left = 0f
            top = 0f
            right = width.toFloat()
            bottom = height.toFloat()
        }

        pathR.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas?.clipPath(pathR)

        super.onDraw(canvas)
    }

    fun setInitials(initials: String) {
        this.initials = initials

        // здесь, видимо, инициалы нужно нарисовать



        //invalidate()
    }
}