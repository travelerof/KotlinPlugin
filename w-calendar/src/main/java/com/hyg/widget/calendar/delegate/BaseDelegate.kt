package com.hyg.widget.calendar.delegate

import android.content.Context
import android.view.View
import com.hyg.widget.calendar.entity.CalendarParams
import com.hyg.widget.calendar.ICalendarView

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    BaseDelegate
 * Author:       hanyonggang
 * Date:         2022/1/14 14:49
 * Description:
 *
 */
abstract class BaseDelegate(
    val context: Context,
    val calendarView: ICalendarView
) : IDelegate {

    /**
     * 是否被挂起
     */
    private var isPause = false


    override fun onHandle(what: Int) {
        if (isPause()) {
            return
        }
    }

    fun resume() {
        isPause = false
    }

    fun pause() {
        isPause = true
    }

    fun isPause(): Boolean = isPause

    override fun <T : View> getViewById(id: Int): T = calendarView.getViewById(id)

    protected fun getParams():CalendarParams = calendarView.getCalendarParams()
}