package com.hyg.widget.calendar.annotation

/**
 * Package:      com.hyg.widget.calendar.annotation
 * ClassName:    HandleWhat
 * Author:       hanyonggang
 * Date:         2022/1/15 21:01
 * Description:
 *
 */
class HandleWhat {

    companion object {
        /**
         * 更新年份
         */
        const val UPDATE_YEAR = 0

        /**
         * 更新月份
         */
        const val UPDATE_MONTH = 1

        /**
         * 查询年份数据
         */
        const val FIND_YEAR = 2

        /**
         * 查询月份数据
         */
        const val FIND_MONTH = 3

        /**
         * 选择view显示
         */
        const val SHOW_CHOICE_VIEW = 4

        /**
         * 选择view隐藏
         */
        const val HIDE_CHOICE_VIEW = 5

        const val END_YEAR_CHANGED_ANIM = 6

        const val END_MONTH_CHANGED_ANIM = 7

        const val PAGE_SCROLL_UPDATE = 8

        const val YEAR_MONTH_CHOICE_UPDATE = 9
    }
}