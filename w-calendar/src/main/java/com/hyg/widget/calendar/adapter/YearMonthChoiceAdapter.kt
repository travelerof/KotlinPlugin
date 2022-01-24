package com.hyg.widget.calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.entity.ChoiceEntity

/**
 * Package:      com.hyg.widget.calendar.adapter
 * ClassName:    YearMonthChoiceAdapter
 * Author:       hanyonggang
 * Date:         2022/1/16 0:18
 * Description:
 *
 */
internal class YearMonthChoiceAdapter(
    private val context: Context,
    private val data: MutableList<ChoiceEntity>
) : RecyclerView.Adapter<YearMonthChoiceAdapter.ChoiceVH>() {
    private var onAdapterItemClickListener: OnAdapterItemClickListener<ChoiceEntity>? = null

    fun setOnAdapterItemClickListener(onAdapterItemClickListener: OnAdapterItemClickListener<ChoiceEntity>) {
        this.onAdapterItemClickListener = onAdapterItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceVH = ChoiceVH(
        LayoutInflater.from(context)
            .inflate(R.layout.adapter_year_month_choice_layout, parent, false)
    )

    override fun onBindViewHolder(holder: ChoiceVH, position: Int) {
        val entity = data[position]
        holder.tvInfo.setTextColor(
            if (entity.selector) context.resources.getColor(R.color.color_3700B3) else context.resources.getColor(
                R.color.color_black_333333
            )
        )
        holder.tvInfo.textSize = if (entity.selector) 14f else 12f
        holder.tvInfo.text = entity.description
        holder.layout.setOnClickListener {
            onAdapterItemClickListener?.onItemClick(
                entity,
                position
            )
        }
    }

    override fun getItemCount(): Int = data.size

    class ChoiceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<LinearLayout>(R.id.adapter_year_month_root_layout)
        val tvInfo = itemView.findViewById<TextView>(R.id.adapter_year_month_tv)
    }
}