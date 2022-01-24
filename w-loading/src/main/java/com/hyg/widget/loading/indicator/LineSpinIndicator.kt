package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.view.animation.LinearInterpolator
import kotlin.math.min

/**
 * Package:      com.hyg.widget.loading.draw
 * ClassName:    DefaultDraw
 * Author:       hanyonggang
 * Date:         2021/12/27 19:39
 * Description:
 *
 */
class LineSpinIndicator(loadView: ILoadView):AbstractIndicator(loadView) {

    companion object{
        private const val TOTAL = 12
        private const val DEGREE_PRE = 360f/TOTAL
    }
    //当前刻度
    private var degree:Float = 0f

    /**
     * 大小
     */
    private var size = 0

    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        size = min(measureWidth,measureHeight)
    }

    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animator = ValueAnimator.ofInt(0, TOTAL - 1)
        animator.duration = 600
        animator.repeatMode = ValueAnimator.RESTART
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        return mutableListOf(AnimatorHelper(animator){
            val value = it.animatedValue as Int
            degree = value* DEGREE_PRE
            loadView.postDraw()
        })
    }

    override fun onDraw(canvas: Canvas) {
        val width = size/16
        val height = size/8
        paint.strokeWidth = width.toFloat()
        canvas.rotate(degree,size/2f,size/2f)
        canvas.translate(size/2f,size/2f)
        for (i in 0 until TOTAL){
            canvas.rotate(DEGREE_PRE)
            paint.alpha = (255f*(i+1)/ TOTAL).toInt()
            canvas.translate(0f,-size/2f+width/2f)
            canvas.drawLine(0f,0f,0f,height.toFloat(),paint)
             canvas.translate(0f,size/2f - width/2f)
        }
    }

    override fun indicatorId(): Int = IndicatorId.LINE_SPIN

}