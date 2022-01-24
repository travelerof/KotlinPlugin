package com.hyg.widget.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyg.widget.calendar.MonthCalendarView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.entity.CalendarPageEntity
import com.hyg.widget.calendar.entity.CalendarParams

/**
 * Package:      com.hyg.widget.calendar.adapter
 * ClassName:    CalendarPageAdapter
 * Author:       hanyonggang
 * Date:         2022/1/16 17:31
 * Description:
 *
 */
class CalendarPageAdapter(
    private val context: Context,
    private val params:CalendarParams,
    private val data: MutableList<CalendarPageEntity>
) : RecyclerView.Adapter<CalendarPageAdapter.CalendarPageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPageVH =
        CalendarPageVH(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_calendar_page_layout, parent, false)
        )

    override fun onBindViewHolder(holder: CalendarPageVH, position: Int) {
        val entity = data[position]
        holder.dayView.initCalendar(entity.year,entity.month, params.weekType, params.calendarItemParams)
    }

    override fun getItemCount(): Int = data.size

    class CalendarPageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayView = itemView.findViewById<MonthCalendarView>(R.id.adapter_page_day_root_view)
    }
}