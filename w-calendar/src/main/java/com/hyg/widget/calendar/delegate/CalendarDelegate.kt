package com.hyg.widget.calendar.delegate

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import com.hyg.widget.calendar.ICalendarView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.adapter.CalendarPageAdapter
import com.hyg.widget.calendar.annotation.HandleWhat
import com.hyg.widget.calendar.entity.CalendarPageEntity

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    CalendarDelegate
 * Author:       hanyonggang
 * Date:         2022/1/14 17:53
 * Description: 日历
 *
 */
class CalendarDelegate(context: Context, calendarView: ICalendarView) :
    BaseDelegate(context, calendarView) {

    private val vp = getViewById<ViewPager2>(R.id.calendar_vp)
    private val data = mutableListOf<CalendarPageEntity>()
    private val adapter = CalendarPageAdapter(context, getParams(), data)

    init {
        vp.adapter = adapter
        data += getCalendarPageData()
        vp.currentItem = getCurrentPosition()
        adapter.notifyDataSetChanged()
        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val entity = data[position]
                getParams().currentYear = entity.year
                getParams().currentMonth = entity.month
                calendarView.onHandle(HandleWhat.PAGE_SCROLL_UPDATE)
            }
        })
    }

    override fun onHandle(what: Int) {
        if (isPause()) {
            return
        }
        when (what) {
            HandleWhat.YEAR_MONTH_CHOICE_UPDATE -> vp.currentItem = getCurrentPosition()
            else -> {}
        }
    }
    private fun getCalendarPageData(): MutableList<CalendarPageEntity> {
        val pageData = mutableListOf<CalendarPageEntity>()
        val minYear = getParams().minYear
        val maxYear = getParams().maxYear
        if (minYear > maxYear) {
            return mutableListOf()
        }
        var count = 0
        for (year in minYear..maxYear) {
            for (month in 1..12) {
                pageData += CalendarPageEntity(count++, year, month)
            }
        }
        return pageData
    }

    private fun getCurrentPosition(): Int {
        val currentYear = getParams().currentYear
        val currentMonth = getParams().currentMonth
        data.forEach {
            if (currentYear == it.year && currentMonth == it.month){
                return it.id
            }
        }
        return 0
    }
}