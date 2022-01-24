package com.hyg.widget.calendar

import android.view.View
import com.hyg.widget.calendar.annotation.ChoiceType
import com.hyg.widget.calendar.entity.CalendarParams

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    ICalendarView
 * Author:       hanyonggang
 * Date:         2022/1/15 20:21
 * Description:
 *
 */
interface ICalendarView {

    fun onHandle(what:Int)

    fun getCalendarParams(): CalendarParams

    fun <T : View> getViewById(id: Int): T

    fun showChoiceView(percent: Float, type: Int)

    fun isShowChoiceView(): Boolean

    fun getChoiceType():Int

}