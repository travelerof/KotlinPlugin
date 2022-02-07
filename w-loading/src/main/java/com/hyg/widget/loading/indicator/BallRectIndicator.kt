package com.hyg.widget.loading.indicator

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.material.badge.BadgeDrawable

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
class BallRectIndicator(loadView: ILoadView):AbstractIndicator(loadView) {
    private var minArcWidth = 0
    private var maxArcWidth = 0
    override fun onMeasure(measureWidth: Int, measureHeight: Int) {
        super.onMeasure(measureWidth, measureHeight)

    }
    override fun createAnimations(): MutableList<AnimatorHelper> {
        val animators = mutableListOf<AnimatorHelper>()
        val valueAnimator = ValueAnimator.ofFloat(0f,1f,0f)
        valueAnimator.duration = 500
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        return animators
    }

    override fun onDraw(canvas: Canvas) {

    }

    override fun indicatorId(): Int = IndicatorId.BALL_RECT
}