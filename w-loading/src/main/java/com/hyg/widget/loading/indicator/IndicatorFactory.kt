package com.hyg.widget.loading.indicator

/**
 * Package:      com.hyg.widget.loading.indicator
 * ClassName:    IndicatorFactory
 * Author:       hanyonggang
 * Date:         2021/12/29 18:22
 * Description:
 *
 */
class IndicatorFactory {

    companion object {
        fun create(@IndicatorId indicatorId: Int, loadView: ILoadView): AbstractIndicator {
            return when (indicatorId) {
                IndicatorId.BALL_SPIN -> BallSpinIndicator(loadView)
                IndicatorId.BALL_PULSE -> BallPulseIndicator(loadView)
                IndicatorId.BALL_RIPPLE -> BallRippleIndicator(loadView)
                IndicatorId.LINE_SCALE -> LineScaleIndicator(loadView)
                IndicatorId.LINE_SCALE_PULSE -> LineScalePulseIndicator(loadView)
                IndicatorId.BALL_SCALE -> BallScaleIndicator(loadView)
                else -> LineSpinIndicator(loadView)
            }
        }
    }
}