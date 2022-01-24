package com.hyg.widget.label

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView

/**
 * Package:      com.hyg.widget.label
 * ClassName:    LabelView
 * Author:       hanyonggang
 * Date:         2022/1/3 13:17
 * Description:
 *
 */
open class LabelView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ViewGroup(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var itemBackgroundId: Int = 0
    private var itemTextSize: Float = 0f
    private var itemTextColor: Int = 0
    private var itemHorizontalPadding: Int = 0
    private var itemVerticalPadding: Int = 0

    private var labelVerticalMargin: Int = 0
    private var labelHorizontalMargin: Int = 0

    private var max: Int = 50

    private val labels = mutableListOf<String>()

    private var onLabelClickListener: OnLabelClickListener? = null

    private val onClickListener = OnClickListener {
        if (it is TextView) {
            onLabelClickListener?.onLabelClick(it.text.toString())
        }
    }

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LabelView)
        try {
            itemBackgroundId = array.getResourceId(
                R.styleable.LabelView_l_background,
                R.drawable.shape_label_default
            )
            max = array.getInt(R.styleable.LabelView_l_max, 100)
            itemTextSize = array.getDimension(
                R.styleable.LabelView_l_textSize,
                resources.getDimension(R.dimen.label_default_textsize)
            ) / resources.displayMetrics.density
            itemTextColor = array.getColor(
                R.styleable.LabelView_l_textColor,
                resources.getColor(R.color.label_textcolor)
            )
            itemHorizontalPadding = array.getDimensionPixelSize(
                R.styleable.LabelView_l_horizontalPadding,
                resources.getDimensionPixelSize(R.dimen.label_horizontal_padding)
            )
            itemVerticalPadding = array.getDimensionPixelSize(
                R.styleable.LabelView_l_verticalPadding,
                resources.getDimensionPixelSize(R.dimen.label_vertical_padding)
            )
            labelHorizontalMargin = array.getDimensionPixelSize(
                R.styleable.LabelView_l_horizontalMargin,
                resources.getDimensionPixelSize(R.dimen.label_margin)
            )
            labelVerticalMargin = array.getDimensionPixelSize(
                R.styleable.LabelView_l_verticalMargin,
                resources.getDimensionPixelSize(R.dimen.label_margin)
            )
        } finally {
            array.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        var startX = labelHorizontalMargin
        var startY = labelVerticalMargin
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childMeasureWidth = child.measuredWidth
            val childMeasureHeight = child.measuredHeight
            if (startX + childMeasureWidth > widthMeasureSize - labelHorizontalMargin) {
                startX = labelHorizontalMargin
                startY += childMeasureHeight + labelVerticalMargin
            }
            child.tag =
                Location(startX, startY, startX + childMeasureWidth, startY + childMeasureHeight)
            startX += childMeasureWidth + labelHorizontalMargin
            if (index == childCount - 1) {
                startY += childMeasureHeight + labelVerticalMargin
            }
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), startY)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val location = child.tag as Location
            child.layout(location.left, location.top, location.right, location.bottom)
        }
    }

    fun addLabel(text: String) {
        if (labels.contains(text)) {
            return
        }
        if (childCount > 0 && childCount >= max) {
            removeIndex(childCount - 1)
        }
        labels += text
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(createItemView(text), 0, params)
        requestLayout()
    }

    fun clearLabels() {
        removeAllViews()
        labels.clear()
    }

    fun setOnLabelClickListener(onLabelClickListener: OnLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener
    }

    private fun removeIndex(index: Int) {
        removeViewAt(index)
        labels.removeAt(index)
    }

    private fun createItemView(text: String): TextView {
        val tv = TextView(context)
        tv.setBackgroundResource(itemBackgroundId)
        tv.setPadding(
            itemHorizontalPadding,
            itemVerticalPadding,
            itemHorizontalPadding,
            itemVerticalPadding
        )
        tv.setTextColor(itemTextColor)
        tv.textSize = itemTextSize
        tv.text = text
        tv.setOnClickListener(onClickListener)
        return tv
    }

    data class Location(val left: Int, val top: Int, val right: Int, val bottom: Int)
}