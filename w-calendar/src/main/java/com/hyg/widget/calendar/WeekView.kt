package com.hyg.widget.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    WeekView
 * Author:       hanyonggang
 * Date:         2022/1/10 14:51
 * Description:
 *
 */
class WeekView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var childTextSize: Float = 12f
    private var childTextColor: Int = 0
    private var childBackgroundColor: Int = 0
    private var childMarginLeft: Int = 0
    private var childMarginTop: Int = 0
    private var childMarginRight: Int = 0
    private var childMarginBottom: Int = 0
    private val weeks = mutableListOf<String>()

    private var onWeekClickListener: OnWeekClickListener? = null

    private val onClickListener = OnClickListener {
        if (it is TextView) {
            onWeekClickListener?.onWeekClick(it.text.toString())
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        orientation = HORIZONTAL
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.WeekView)
        try {
            childTextSize = array.getDimension(
                R.styleable.WeekView_w_childTextSize,
                resources.getDimension(R.dimen.calendar_week_textsize)
            ) / resources.displayMetrics.density
            childTextColor = array.getColor(
                R.styleable.WeekView_w_childTextColor,
                resources.getColor(R.color.color_black_333333)
            )
            childBackgroundColor = array.getColor(
                R.styleable.WeekView_w_childBackgroundColor,
                Color.TRANSPARENT
            )
            childMarginLeft = array.getDimensionPixelSize(R.styleable.WeekView_w_childMarginLeft, 0)
            childMarginTop = array.getDimensionPixelSize(R.styleable.WeekView_w_childMarginTop, 0)
            childMarginRight =
                array.getDimensionPixelSize(R.styleable.WeekView_w_childMarginRight, 0)
            childMarginBottom =
                array.getDimensionPixelSize(R.styleable.WeekView_w_childMarginBottom, 0)
        } finally {
            array.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val defaultValue = resources.getDimensionPixelSize(R.dimen.week_default_height)
        val measureWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (measureHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measureWidthSize, defaultValue)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (!changed) {
            return
        }
        for (index in 0 until childCount){
            val childView = getChildAt(index)
            val params = childView.layoutParams
            params.height = measuredHeight
            childView.layoutParams = params
        }
    }
    override fun setOrientation(orientation: Int) {

    }

    fun setOnWeekClickListener(onWeekClickListener: OnWeekClickListener?) {
        this.onWeekClickListener = onWeekClickListener
    }

    fun setWeekData(weeks: MutableList<String>) {
        this.weeks.clear()
        this.weeks += weeks
        addViews()
    }


    fun setWeekData(weeks:Array<String>){

        val data = mutableListOf<String>()
        weeks.forEach {
            data += it
        }
        setWeekData(data)
    }

    private fun addViews() {
        if (weeks.size > childCount) {
            val diff = weeks.size - childCount
            for (index in 0 until diff) {
                val tv = createChildView()
                val params = LayoutParams(0, LayoutParams.MATCH_PARENT)
                params.weight = 1f
                params.leftMargin = childMarginLeft
                params.rightMargin = childMarginRight
                params.topMargin = childMarginTop
                params.bottomMargin = childMarginBottom
                addView(tv, params)
            }
        } else if (weeks.size < childCount) {
            val diff = childCount - weeks.size
            removeViews(weeks.size, diff)
        }
        updateChildView()
    }


    private fun updateChildView() {
        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            if (childView is TextView) {
                childView.text = weeks[index]
            }
        }
    }

    private fun createChildView(): TextView {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.textSize = childTextSize
        tv.setTextColor(childTextColor)
        tv.setBackgroundColor(childBackgroundColor)
        tv.setOnClickListener(onClickListener)
        return tv
    }

    interface OnWeekClickListener {
        fun onWeekClick(text: String)
    }
}