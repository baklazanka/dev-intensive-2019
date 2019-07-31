package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ImageView(context, attrs, defStyleAttr) {
    companion object{
        private const val DEFAULT_CV_BORDERCOLOR = 0
        private const val DEFAULT_CV_BORDERWIDTH = 2
    }

    private var cv_borderColor = DEFAULT_CV_BORDERCOLOR
    private var cv_borderWidth = DEFAULT_CV_BORDERWIDTH

    init {
        if (attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_CV_BORDERCOLOR)
            cv_borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_CV_BORDERWIDTH)
            a.recycle()
        }
    }

    fun getBorderWidth(): Int {
        return cv_borderWidth
    }

    //fun setBorderWidth(@Dimension(unit = Dimension.DP) dp: Int) {
    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = dp
    }

    fun getBorderColor(): Int {
        return cv_borderColor
    }

    fun setBorderColor(hex: String) {
        //cv_borderColor = ContextCompat.
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        cv_borderColor = colorId
    }
//(https://)?(www.)?github.com/(\w*(-)?\w{2,}[^/])    регулярка для последнего
}