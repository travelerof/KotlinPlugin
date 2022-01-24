package com.hyg.widget.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.hyg.widget.calendar.annotation.ChoiceType
import com.hyg.widget.calendar.annotation.HandleWhat
import com.hyg.widget.calendar.delegate.CalendarDelegate
import com.hyg.widget.calendar.delegate.WeekDelegate
import com.hyg.widget.calendar.delegate.YearMonthChoiceDelegate
import com.hyg.widget.calendar.delegate.YearMonthDelegate
import com.hyg.widget.calendar.entity.CalendarParams

/**
 * Package:      com.hyg.widget.calendar
 * ClassName:    CalendarView
 * Author:       hanyonggang
 * Date:         2022/1/10 10:29
 * Description:
 *
 */
class CalendarView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr), ICalendarView {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val params = CalendarParams(context, attrs)

    /**
     * 年月展示委托
     */
    private val yearMonthDelegate: YearMonthDelegate

    /**
     * 星期委托
     */
    private val weekDelegate: WeekDelegate

    /**
     * 日历展示委托
     */
    private val calendarDelegate: CalendarDelegate

    /**
     * 选择年月委托
     */
    private val yearMonthChoiceDelegate: YearMonthChoiceDelegate


    init {
        View.inflate(context, R.layout.layout_calendar, this)
        yearMonthDelegate = YearMonthDelegate(context, this)
        weekDelegate = WeekDelegate(context, this)
        calendarDelegate = CalendarDelegate(context, this)
        yearMonthChoiceDelegate = YearMonthChoiceDelegate(context, this)
    }

    override fun onHandle(what: Int) {
        when (what) {
            HandleWhat.UPDATE_YEAR -> yearMonthDelegate.onHandle(what)
            HandleWhat.UPDATE_MONTH -> yearMonthDelegate.onHandle(what)
            HandleWhat.END_YEAR_CHANGED_ANIM -> yearMonthDelegate.onHandle(what)
            HandleWhat.END_MONTH_CHANGED_ANIM -> yearMonthDelegate.onHandle(what)
            HandleWhat.HIDE_CHOICE_VIEW -> yearMonthChoiceDelegate.onHandle(what)
            HandleWhat.PAGE_SCROLL_UPDATE -> {
                yearMonthDelegate.onHandle(HandleWhat.UPDATE_YEAR)
                yearMonthDelegate.onHandle(HandleWhat.UPDATE_MONTH)
            }
            HandleWhat.YEAR_MONTH_CHOICE_UPDATE -> calendarDelegate.onHandle(what)
            else -> {}
        }
    }

    override fun getCalendarParams(): CalendarParams = params

    override fun <T : View> getViewById(id: Int): T = findViewById(id)

    override fun showChoiceView(percent: Float, type: Int) {
        yearMonthChoiceDelegate.setPercent(percent)
        if (type == ChoiceType.MONTH) {
            yearMonthChoiceDelegate.onHandle(HandleWhat.FIND_MONTH)
        } else {
            yearMonthChoiceDelegate.onHandle(HandleWhat.FIND_YEAR)
        }
        yearMonthChoiceDelegate.onHandle(HandleWhat.SHOW_CHOICE_VIEW)
    }

    override fun isShowChoiceView(): Boolean = yearMonthChoiceDelegate.isShowChoiceView()
    override fun getChoiceType(): Int = yearMonthChoiceDelegate.getChoiceType()
}