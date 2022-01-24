package com.hyg.widget.calendar.annotation

import androidx.annotation.IntDef

/**
 * Package:      com.hyg.widget.calendar.annotation
 * ClassName:    Week
 * Author:       hanyonggang
 * Date:         2022/1/20 20:43
 * Description:
 *
 */
@IntDef(Week.MONDAY, Week.SATURDAY, Week.SUNDAY)
@Retention(AnnotationRetention.SOURCE)
annotation class Week {
    companion object {
        /**
         * 周一
         */
        const val MONDAY = 0

        /**
         * 周六
         */
        const val SATURDAY = 1

        /**
         * 周日
         */
        const val SUNDAY = 2
    }
}