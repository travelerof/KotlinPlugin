package com.hyg.widget.calendar.annotation

import androidx.annotation.IntDef

/**
 * Package:      com.hyg.widget.calendar.annotation
 * ClassName:    CalendarType
 * Author:       hanyonggang
 * Date:         2022/1/21 20:52
 * Description:
 *
 */
@IntDef(MonthType.CURRENT,MonthType.LAST,MonthType.NEXT)
@Retention(AnnotationRetention.SOURCE)
annotation class MonthType {
    companion object{
        /**
         * 下一月
         */
        const val CURRENT = 0

        /**
         * 当前月
         */
        const val LAST = 1

        /**
         * 下一月
         */
        const val NEXT = 2
    }
}