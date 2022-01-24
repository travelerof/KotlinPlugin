package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import kotlin.math.min

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    BallScaleIndicator
 * Author:       hanyonggang
 * Date:         2021/12/31 0:34
 * Description:
 *
 */
class BallScaleIndicator(loadView: ILoadView):AbstractIndicator(loadView) {
    companion object{
        private const val MARGIN = 10
    }
    private var size: Int = 0
    private var minRadius:Float = 0f
    private var maxRadius:Float = 0f

    private var scale:Float = 0f

    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)
        size = min(measureWidth,measureHeight)
        maxRadius = (size - MARGIN)/2f
        minRadius = maxRadius/4
    }
    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animator = ValueAnimator.ofFloat(0f,1f)
        animator.duration = 1000
        animator.repeatCount = ValueAnimator.INFINITE
        return mutableListOf(
            AnimatorHelper(animator){
                scale = it.animatedValue as Float
                loadView.postDraw()
            }
        )
    }

    override fun onDraw(canvas: Canvas) {
        paint.alpha = (255 - 255*scale).toInt()
        canvas.scale(scale,scale,measureWidth/2f,measureHeight/2f)
        paint.alpha = (255 - 255*scale).toInt()
        canvas.drawCircle(measureWidth/2f,measureHeight/2f,minRadius+(maxRadius - minRadius)*scale,paint)
    }

    override fun indicatorId(): Int = IndicatorId.BALL_SCALE
}