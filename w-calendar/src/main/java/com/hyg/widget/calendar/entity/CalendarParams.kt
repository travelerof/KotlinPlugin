package com.hyg.widget.calendar.entity

import android.content.Context
import android.util.AttributeSet
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.annotation.Week

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    CalendarParams
 * Author:       hanyonggang
 * Date:         2022/1/13 23:06
 * Description:
 *
 */
class CalendarParams(private val context: Context, val attrs: AttributeSet?) {

    /**
     * 是否显示年月选择底部分割线
     */
    var showYearLine: Boolean = false

    /**
     * 星期bar，开始显示星期
     */
    @Week
    var weekType: Int = Week.MONDAY

    /**
     * 是否显示星期bar底部分割线
     */
    var showWeekLine: Boolean = false

    /**
     * 最小年份
     */
    var minYear: Int = 1950

    /**
     * 最大年份
     */
    var maxYear: Int = 2023

    /**
     * 当前年
     */
    var currentYear: Int = 2022

    /**
     * 当前月
     */
    var currentMonth: Int = 1

    /**
     * 日历item
     */
    val calendarItemParams = CalendarItemParams(
        context.resources.getDimensionPixelSize(R.dimen.week_default_height)
    )

    data class CalendarItemParams(var calendarItemHeight: Int) {
        /**
         * 日历表是否补齐其他月份显示
         */
        var showOtherMonth: Boolean = true

        /**
         * 是否显示农历
         */
        var showLunarCalendar: Boolean = true

        /**
         * 日历是否加粗
         */
        var isCalendarBoldText = true
        /**
         * 当前月日历文本颜色
         */
        var calendarTextColor: Int = 0

        /**
         * 当前月日历文本大小
         */
        var calendarTextSize: Float = 0f

    }
}