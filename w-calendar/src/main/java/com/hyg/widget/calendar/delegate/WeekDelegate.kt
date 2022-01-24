package com.hyg.widget.calendar.delegate

import android.content.Context
import android.view.View
import com.hyg.widget.calendar.ICalendarView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.WeekView
import com.hyg.widget.calendar.annotation.Week

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    WeekDelegate
 * Author:       hanyonggang
 * Date:         2022/1/14 14:55
 * Description:
 *
 */
class WeekDelegate(context: Context, calendarView: ICalendarView) :
    BaseDelegate(context, calendarView) {

    private val weekView = getViewById<WeekView>(R.id.week_view)

    private val line = getViewById<View>(R.id.week_line_view)

    init {
        line.visibility = if (getParams().showWeekLine) View.VISIBLE else View.GONE
        weekView.setWeekData(getWeekData())
    }
    private fun getWeekData():Array<String> = when (getParams().weekType) {
        Week.SATURDAY -> context.resources.getStringArray(R.array.weeks_saturday)
        Week.MONDAY -> context.resources.getStringArray(R.array.weeks_monday)
        else -> context.resources.getStringArray(R.array.weeks_default)
    }
}