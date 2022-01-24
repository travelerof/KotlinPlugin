package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import kotlin.math.min

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    BallRippleIndicator
 * Author:       hanyonggang
 * Date:         2021/12/30 21:07
 * Description:
 *
 */
class BallRippleIndicator(loadView:ILoadView):AbstractIndicator(loadView) {
    companion object{
        private const val TOTAL = 3
        private val delays = longArrayOf(120,240,360)
    }
    private val scales = floatArrayOf(1f,1f,1f)

    private var minHeight:Float = 0f
    private var maxHeight:Float = 0f

    private val points = mutableListOf<PointF>()
    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        minHeight = measureHeight*0.25f
        maxHeight = measureHeight*0.75f
        paint.strokeWidth = minHeight
        points.clear()
        var startX = measureWidth/2 - minHeight
        for (index in 0 until TOTAL){
            val point = PointF()
            point.x = startX
            points += point
            startX += minHeight
        }
    }
    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animators = mutableListOf<AnimatorHelper>()
        for (index in 0 until TOTAL){
            val scaleAnimator = ValueAnimator.ofFloat(1f,0f,1f)
            scaleAnimator.duration = 800
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
            canvas.drawPoint(point.x,maxHeight - (maxHeight - minHeight)*scales[index],paint)
        }
    }

    override fun indicatorId(): Int = IndicatorId.BALL_RIPPLE
}