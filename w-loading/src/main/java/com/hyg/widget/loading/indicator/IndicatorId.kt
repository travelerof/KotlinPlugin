package com.hyg.widget.loading.indicator

import androidx.annotation.IntDef

/**
 * Package:      com.hyg.widget.loading
 * ClassName:    LoadType
 * Author:       hanyonggang
 * Date:         2021/12/27 19:37
 * Description:
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    IndicatorId.LINE_SPIN,
    IndicatorId.BALL_SPIN,
    IndicatorId.BALL_PULSE,
    IndicatorId.BALL_RIPPLE,
    IndicatorId.LINE_SCALE,
    IndicatorId.LINE_SCALE_PULSE,
    IndicatorId.BALL_SCALE
)
annotation class IndicatorId {
    companion object {
        const val LINE_SPIN = 0
        const val BALL_SPIN = 1
        const val BALL_PULSE = 2
        const val BALL_RIPPLE = 3
        const val LINE_SCALE = 4
        const val LINE_SCALE_PULSE = 5
        const val BALL_SCALE = 6
    }
}