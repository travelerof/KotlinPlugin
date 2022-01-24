package com.hyg.widget.calendar.annotation

import androidx.annotation.IntDef

/**
 * Package:      com.hyg.widget.calendar.annotation
 * ClassName:    ChoiceType
 * Author:       hanyonggang
 * Date:         2022/1/16 14:55
 * Description:
 *
 */
@IntDef(ChoiceType.YEAR, ChoiceType.MONTH)
@Retention(AnnotationRetention.SOURCE)
annotation class ChoiceType {
    companion object {
        const val YEAR = 0
        const val MONTH = 1
    }
}