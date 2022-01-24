package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Point
import kotlin.math.min

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    BallPulseIndicator
 * Author:       hanyonggang
 * Date:         2021/12/30 20:28
 * Description:
 *
 */
class BallPulseIndicator(loadView:ILoadView):AbstractIndicator(loadView) {
    companion object{
        private const val TOTAL = 3
        private val delays = longArrayOf(120,240,360)
    }

    private var minPaintSize: Float = 0f
    private var maxPaintSize: Float = 0f

    private val points = mutableListOf<Point>()

    private val scales = floatArrayOf(1f,1f,1f)

    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        val size = min(measureWidth,measureHeight)
        maxPaintSize = size/3.5f
        minPaintSize = maxPaintSize/2.5f
        var startX = (measureWidth/2 - maxPaintSize).toInt()
        points.clear()
        for (index in 0 until TOTAL){
            val point = Point()
            point.x = startX
            point.y = measureHeight/2
            points += point
            startX += maxPaintSize.toInt()
        }

    }

    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animators = mutableListOf<AnimatorHelper>()
        for (index in 0 until TOTAL){
            val scaleAnimator = ValueAnimator.ofFloat(1f,0f,1f)
            scaleAnimator.duration = 700
            scaleAnimator.repeatCount = ValueAnimator.INFINITE
            scaleAnimator.startDelay = delays[index]
            animators += AnimatorHelper(scaleAnimator){
                scales[index] = it.animatedValue as Float
                loadView.postDraw()
            }
        }
        return animators
    }

    override fun onDraw(canvas: Canvas) {
        for (index in 0 until points.size){
            val point = points[index]
            paint.strokeWidth = maxPaintSize - (maxPaintSize - minPaintSize)*scales[index]
            canvas.drawPoint(point.x.toFloat(),point.y.toFloat(),paint)
        }
    }

    override fun indicatorId(): Int = IndicatorId.BALL_PULSE

}