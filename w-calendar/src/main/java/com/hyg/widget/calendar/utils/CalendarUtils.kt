package com.hyg.widget.calendar.utils

import com.hyg.widget.calendar.annotation.MonthType
import com.hyg.widget.calendar.annotation.Week
import com.hyg.widget.calendar.entity.DayEntity
import java.util.*

/**
 * Package:      com.hyg.widget.calendar.utils
 * ClassName:    CalendarUtils
 * Author:       hanyonggang
 * Date:         2022/1/21 16:51
 * Description:
 *
 */
class CalendarUtils {
    companion object {

        const val MAX_SIZE = 42

        /**
         * 获取日历
         * @param year Int
         * @param month Int
         * @param weekType Int
         * @param showOtherMonth Boolean
         * @return MutableList<DayEntity>
         */
        @JvmStatic
        fun getMonthCalendar(
            year: Int,
            month: Int,
            @Week weekType: Int,
            showOtherMonth: Boolean
        ): MutableList<DayEntity> {
            val calendar = Calendar.getInstance()
            //设置当月
            calendar.set(year, month - 1, 1)
            //当月1号星期
            val monthFirstDayWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val weekOffset = getMonthFirstDayWeekOffset(monthFirstDayWeek, weekType)

            val entities = mutableListOf<DayEntity>()
            entities += getLastMonthCalendar(
                year,
                month,
                monthFirstDayWeek,
                weekOffset,
                showOtherMonth
            )
            entities += getCurrentMonthCalendar(year, month)
            entities += getNextMonthCalendar(
                year,
                month,
                MAX_SIZE - entities.size,
                showOtherMonth
            )
            return entities
        }

        /**
         * 获取当前月日历
         *
         * @param year Int
         * @param month Int
         * @return MutableList<DayEntity>
         */
        @JvmStatic
        fun getCurrentMonthCalendar(
            year: Int,
            month: Int
        ): MutableList<DayEntity> {
            //当月最后一天
            val lastDay = getMonthLastDay(year, month)

            val entities = mutableListOf<DayEntity>()
            for (day in 1..lastDay) {
                val entity = DayEntity()
                entity.year = year
                entity.month = month
                entity.day = day
                entities += entity
            }
            return entities
        }

        /**
         * 补齐下个月日历
         *
         * @param year Int
         * @param month Int
         * @param offset Int
         * @param showOtherMonth Boolean
         * @return MutableList<DayEntity>
         */
        @JvmStatic
        fun getNextMonthCalendar(
            year: Int,
            month: Int,
            offset: Int,
            showOtherMonth: Boolean
        ): MutableList<DayEntity> {
            if (offset <= 0) {
                return mutableListOf()
            }
            var nextYear: Int
            var nextMonth: Int
            if (month == 12) {
                nextYear = year + 1
                nextMonth = 1
            } else {
                nextYear = year
                nextMonth = month + 1
            }
            val entities = mutableListOf<DayEntity>()
            for (day in 1..offset) {
                val entity = DayEntity()
                entity.show = showOtherMonth
                entity.year = nextYear
                entity.month = nextMonth
                entity.day = day
                entity.monthType = MonthType.LAST
                entities += entity
            }
            return entities
        }

        /**
         * 补齐上个月日历
         *
         * @param year Int
         * @param month Int
         * @param monthFirstDayWeek Int
         * @param weekOffset Int
         * @param showOtherMonth Boolean
         * @return MutableList<DayEntity>
         */
        @JvmStatic
        private fun getLastMonthCalendar(
            year: Int,
            month: Int,
            monthFirstDayWeek: Int,
            weekOffset: Int,
            showOtherMonth: Boolean
        ): MutableList<DayEntity> {
            if (weekOffset <= 0) {
                return mutableListOf()
            }
            var lastYear: Int
            var lastMonth: Int
            if (month == 1) {
                lastYear = year - 1
                lastMonth = 12
            } else {
                lastYear = year
                lastMonth = month - 1
            }
            val lastDay = getMonthLastDay(lastYear, lastMonth)
            var week = monthFirstDayWeek
            val entities = mutableListOf<DayEntity>()
            for (index in 0 until weekOffset) {
                val entity = DayEntity()
                entity.show = showOtherMonth
                entity.year = lastYear
                entity.month = lastMonth
                entity.day = lastDay - (weekOffset - 1 - index)
                entity.monthType = MonthType.LAST
                entities += entity
            }
            return entities
        }

        /**
         * 根据年月获取月份最后一天
         *
         * @param year Int
         * @param month Int
         * @return Int
         */
        @JvmStatic
        fun getMonthLastDay(year: Int, month: Int): Int = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> {//2月
                if (isLeapYear(year)) 29 else 28
            }
        }

        /**
         * 是否是闰年
         *
         * @param year Int
         * @return Boolean
         */
        @JvmStatic
        fun isLeapYear(year: Int): Boolean =
            ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)

        /**
         * 获取
         *
         * @param monthFirstDayWeek Int
         * @param week Week
         * @return Int
         */
        @JvmStatic
        private fun getMonthFirstDayWeekOffset(monthFirstDayWeek: Int, @Week weekType: Int): Int =
            when (weekType) {
                Week.SATURDAY -> {
                    if (monthFirstDayWeek == 7) {
                        0
                    } else {
                        monthFirstDayWeek
                    }
                }
                Week.MONDAY -> {
                    if (monthFirstDayWeek == 1) {
                        6
                    } else {
                        monthFirstDayWeek - 2
                    }
                }
                else -> monthFirstDayWeek - 1
            }
    }
}