package com.hyg.widget.calendar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    ChoiceBgView
 * Author:       hanyonggang
 * Date:         2022/1/15 23:03
 * Description:
 *
 */
internal class YearChoiceBgLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private const val MARGIN = 5f
        private const val TOP_MARGIN = 10f
        private const val RADIUS = 10f
    }

    private var percent = 0.8f
    private val paint = Paint()

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        setBackgroundColor(Color.TRANSPARENT)
        setPadding(
            resources.getDimensionPixelSize(R.dimen.dimen_8),
            resources.getDimensionPixelSize(R.dimen.dimen_12),
            resources.getDimensionPixelSize(R.dimen.dimen_8),
            resources.getDimensionPixelSize(R.dimen.dimen_8)
        )
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#ffffff")
    }

    fun setPercent(percent: Float){
        this.percent = percent
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val currentX = width * percent
        val topLeftRectF = RectF(
            MARGIN,
            MARGIN + TOP_MARGIN,
            MARGIN + RADIUS * 2,
            MARGIN + TOP_MARGIN + RADIUS * 2
        )
        val topRightRectF = RectF(
            width - MARGIN - RADIUS * 2,
            MARGIN + TOP_MARGIN,
            width - MARGIN,
            MARGIN + TOP_MARGIN + RADIUS * 2
        )
        val bottomLeftRectF =
            RectF(MARGIN, height - MARGIN - RADIUS * 2, MARGIN + RADIUS * 2, height - MARGIN)
        val bottomRightRectF = RectF(
            width - MARGIN - RADIUS * 2,
            height - MARGIN - RADIUS * 2,
            width - MARGIN,
            height - MARGIN
        )

        val path = Path()
        path.moveTo(currentX, 0f)
        path.lineTo(currentX + MARGIN * 2.5f, MARGIN + TOP_MARGIN)
        path.lineTo(width - MARGIN - RADIUS, MARGIN + TOP_MARGIN)
        path.arcTo(topRightRectF, 270f, 90f)
        path.lineTo(width - MARGIN, height - MARGIN - RADIUS)
        path.arcTo(bottomRightRectF, 0f, 90f)
        path.lineTo(MARGIN + RADIUS, height - MARGIN)
        path.arcTo(bottomLeftRectF, 90f, 90f)
        path.lineTo(MARGIN, MARGIN + TOP_MARGIN + RADIUS)
        path.arcTo(topLeftRectF, 180f, 90f)
        path.lineTo(currentX - MARGIN * 2.5f, MARGIN + TOP_MARGIN)
        path.close()

        canvas?.drawPath(path, paint)

    }
}