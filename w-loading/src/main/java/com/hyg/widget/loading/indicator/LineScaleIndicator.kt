package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    LineScaleIndicator
 * Author:       hanyonggang
 * Date:         2021/12/30 23:13
 * Description:
 *
 */
class LineScaleIndicator(loadView: ILoadView):AbstractIndicator(loadView) {

    companion object{
        private const val TOTAL = 5
        private const val MARGIN = 10
    }

    private var minHeight:Float = 0f
    private var maxHeight:Float = 0f
    private val scales = floatArrayOf(1f,1f,1f,1f,1f)
    private val lineXs = mutableListOf<Float>()
    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        val paintWidth = (measureWidth - MARGIN*6)/12f
        paint.strokeWidth = paintWidth
        maxHeight = measureHeight*1f
        minHeight = maxHeight/2
        var startX = measureWidth/2 - MARGIN*2 - paintWidth*2
        lineXs.clear()
        for (index in 0 until TOTAL){
            lineXs += startX
            startX += MARGIN+paintWidth
        }
    }
    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animators = mutableListOf<AnimatorHelper>()
        val delays = longArrayOf(100,200,300,400,500)
        for (index in 0 until TOTAL){
            val animator = ValueAnimator.ofFloat(1f,0f,1f)
            animator.duration = 800
            animator.repeatCount = ValueAnimator.INFINITE
            animator.startDelay = delays[index]
            animators += AnimatorHelper(animator){
                scales[index] = it.animatedValue as Float
                loadView.postDraw()
            }
        }
        return animators
    }

    override fun onDraw(canvas: Canvas) {
        for (index in 0 until lineXs.size){
            val h = (maxHeight - minHeight)*scales[index]
            canvas.drawLine(lineXs[index],measureHeight/2f - h/2f,lineXs[index],measureHeight/2f+h/2f,paint)
        }
    }

    override fun indicatorId(): Int = IndicatorId.LINE_SCALE
}