package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    BallSpinIndicator
 * Author:       hanyonggang
 * Date:         2021/12/29 18:07
 * Description:
 *
 */
class BallSpinIndicator(loadView: ILoadView) : AbstractIndicator(loadView) {

    companion object {
        /**
         * 总点数
         */
        private const val BALL_COUNT = 8

        /**
         * 360平分8份
         */
        private const val PRE = 360f / BALL_COUNT

        /**
         * 动画掩饰时间
         */
        private val delays = intArrayOf(0, 120, 240, 360, 480, 600, 720, 780)
    }


    private val alphas = arrayOf(255, 255, 255, 255, 255, 255, 255, 255)

    private val scales = floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f)

    /**
     * 加载view矩形尺寸
     */
    private var size = 0

    /**
     * 画笔最小宽度
     */
    private var minPaintSize = 0f

    /**
     * 画笔最大宽度
     */
    private var maxPaintSize = 0f

    private val points = mutableListOf<Point>()

    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        size = min(measureWidth, measureHeight)
        minPaintSize = size / 20f
        maxPaintSize = minPaintSize * 3
        val maxRadius = size / 2f - maxPaintSize

        points.clear()
        for (index in 0 until BALL_COUNT) {
            val point = Point()
            point.index = index
            //角度转换弧度
            val radian = Math.toRadians((-90 + index * PRE).toDouble()).toFloat()
            point.x = measureWidth / 2f + maxRadius * cos(radian)
            point.y = measureHeight / 2f + maxRadius * sin(radian)
            point.alpha = 255 - (178 * index.toFloat() / BALL_COUNT).toInt()
            points += point
        }
    }

    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animators = mutableListOf<AnimatorHelper>()
        for (index in 0 until BALL_COUNT) {
            animators += createAlphaAnimator(index)
            animators += createScaleAnimator(index)
        }
        return animators
    }


    private fun createScaleAnimator(index: Int): AnimatorHelper {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 0f, 1f)
        scaleAnimator.duration = 1_000
        scaleAnimator.repeatCount = ValueAnimator.INFINITE
        scaleAnimator.startDelay = delays[index].toLong()
        return AnimatorHelper(
            scaleAnimator
        ) {
            scales[index] = it.animatedValue as Float
            loadView.postDraw()
        }
    }

    private fun createAlphaAnimator(index: Int): AnimatorHelper {
        val alphaAnimator = ValueAnimator.ofInt(255, 50, 255)
        alphaAnimator.duration = 1_000
        alphaAnimator.repeatCount = ValueAnimator.INFINITE
        alphaAnimator.startDelay = delays[index].toLong()
        return AnimatorHelper(
            alphaAnimator
        ) {
            alphas[index] = it.animatedValue as Int
            loadView.postDraw()
        }
    }

    override fun onDraw(canvas: Canvas) {

        for (index in 0 until points.size) {
            val point = points[index]
            paint.strokeWidth = minPaintSize + (maxPaintSize - minPaintSize) * scales[index]
            paint.alpha = alphas[index]
            canvas.drawPoint(point.x, point.y, paint)
        }
    }


    override fun indicatorId(): Int = IndicatorId.BALL_SPIN

    class Point {
        var index: Int = 0
        var x: Float = 0f
        var y: Float = 0f
        var alpha: Int = 255
    }
}