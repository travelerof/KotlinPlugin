package com.hyg.widget.calendar

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hyg.widget.calendar.annotation.MonthType
import com.hyg.widget.calendar.annotation.Week
import com.hyg.widget.calendar.entity.CalendarParams
import com.hyg.widget.calendar.entity.DayEntity
import com.hyg.widget.calendar.utils.CalendarUtils

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    MonthCalendarView
 * Author:       hanyonggang
 * Date:         2022/1/17 10:20
 * Description:
 *
 */
class MonthCalendarView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val textPaint = TextPaint()
    private val otherTextPaint = TextPaint()

    private val selectedTextPaint = TextPaint()
    private val paint = Paint()
    private val points = mutableListOf<RectF>()
    private val items = mutableListOf<DayEntity>()
    private lateinit var params: CalendarParams.CalendarItemParams

    private var itemWidth: Int = 0
    private var itemHeight: Int =
        context.resources.getDimensionPixelSize(R.dimen.week_default_height)

    init {
        textPaint.isAntiAlias = true
        textPaint.color = Color.parseColor("#333333")
        textPaint.isFakeBoldText = true
        textPaint.textSize = 40f

        otherTextPaint.isAntiAlias = true
        otherTextPaint.color = Color.parseColor("#c0c0c0")
        otherTextPaint.textSize = 40f

        selectedTextPaint.isAntiAlias = true
        selectedTextPaint.color = Color.parseColor("#f0f0f0")
    }

    fun initCalendar(
        year: Int,
        month: Int,
        @Week weekType: Int,
        params: CalendarParams.CalendarItemParams
    ) {
        this.params = params
        items.clear()
        items +=
            CalendarUtils.getMonthCalendar(year, month, weekType, params.showOtherMonth)
        itemHeight = params.calendarItemHeight
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(measureWidthSize, itemHeight * 7)
        itemWidth = measuredWidth / 7
        refreshPoints()
    }

    private fun refreshPoints() {
        points.clear()
        var startX = 0f
        var startY = 0f
        for (position in 1..items.size) {
            points += RectF(startX, startY, startX + itemWidth, startY + itemHeight)
            if (position % 7 == 0) {
                startY += itemHeight
                startX = 0f
            } else {
                startX += itemWidth
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawText(canvas)
        }
    }

    private fun drawText(canvas: Canvas) {
        for (position in points.indices) {
            val rectF = points[position]
            val entity = items[position]
            if (!entity.show) {
                continue
            }
            val day = "${entity.day}"
            val textRect = Rect()
            val textPaint = getTargetTextPaint(entity.monthType)
            textPaint.getTextBounds(day, 0, day.length, textRect)
            canvas.drawText(
                day,
                rectF.left + itemWidth / 2 - textRect.width() / 2,
                rectF.top + itemHeight / 2 + textRect.height() / 2,
                textPaint
            )

        }
    }

    private fun getTargetTextPaint(@MonthType type: Int): TextPaint =
        when (type) {
            MonthType.LAST, MonthType.NEXT -> otherTextPaint
            else -> textPaint
        }
}