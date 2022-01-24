package com.hyg.widget.loading.indicator

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint

/**
 * Package:      com.hyg.widget.loading.draw
 * ClassName:    AbstractDraw
 * Author:       hanyonggang
 * Date:         2021/12/29 0:00
 * Description:
 *
 */
abstract class AbstractIndicator(val loadView: ILoadView) {

    var measureWidth: Int = 0
    var measureHeight: Int = 0

    val paint = Paint()

    private val animators: MutableList<AnimatorHelper> = createAnimations()

    fun initPaint():Paint {
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.FILL
        return paint
    }

    open fun onMeasure(measureWidth: Int, measureHeight: Int) {
        this.measureWidth = measureWidth
        this.measureHeight = measureHeight
    }

    abstract fun createAnimations(): MutableList<AnimatorHelper>

    abstract fun onDraw(canvas: Canvas)

    open fun start() {
        if (isStarted()) {
            return
        }
        for (animator in animators) {
            animator.animatorListener?.let {
                animator.animator.addUpdateListener(it)
            }
            animator.animator.start()
        }
    }

    open fun stop() {
        for (animator in animators) {
            if (animator.animator.isStarted) {
                animator.animator.removeAllUpdateListeners()
                animator.animator.end()
            }
        }
    }

    fun isStarted(): Boolean {
        var status = false
        var count = 0
        for (helper in animators) {
            if (helper.animator.isStarted) {
                count++
            }
        }
        if (count > animators.size.toFloat() / 3 * 2) {
            status = true
        }
        return status
    }

    @IndicatorId
    abstract fun indicatorId(): Int

    data class AnimatorHelper(
        val animator: ValueAnimator,
        var animatorListener: ValueAnimator.AnimatorUpdateListener?
    ) {
        constructor(
            animator: ValueAnimator
        ) : this(animator, null)

    }
}