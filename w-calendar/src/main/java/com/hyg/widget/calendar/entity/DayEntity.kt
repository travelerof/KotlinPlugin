package com.hyg.widget.calendar.entity

import com.hyg.widget.calendar.annotation.MonthType

/**
 * Package:      com.hyg.widget.calendar.entity
 * ClassName:    DayEntity
 * Author:       hanyonggang
 * Date:         2022/1/16 20:00
 * Description:
 *
 */
class DayEntity {
    var year: Int = 0

    var month: Int = 0

    var day: Int = 0

    /**
     * 是否显示
     */
    var show: Boolean = true

    /**
     * 是否为当前月
     */
    @MonthType
    var monthType: Int = MonthType.CURRENT

    /**
     * 星期
     */
    var week: Int = 0
}