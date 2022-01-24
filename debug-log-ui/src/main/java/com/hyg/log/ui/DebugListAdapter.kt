package com.hyg.log.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyg.log.data.DataType
import com.hyg.log.data.model.LogModel

/**
 * Package:      com.hyg.log.ui
 * ClassName:    DebugListAdapter
 * Author:       hanyonggang
 * Date:         2022/1/4 13:49
 * Description:
 *
 */
class DebugListAdapter(private val context: Context, private val data: MutableList<LogModel>) :
    RecyclerView.Adapter<DebugListAdapter.DebugListViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var type = DataType.LOG
    private var onLogItemClickListener: OnLogItemClickListener? = null
    fun setType(@DataType type: Int) {
        this.type = type
    }

    fun setOnLogItemClickListener(onLogItemClickListener: OnLogItemClickListener){
        this.onLogItemClickListener = onLogItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugListViewHolder =
        DebugListViewHolder(inflater.inflate(R.layout.adapter_debug_list_item, parent, false))

    override fun onBindViewHolder(holder: DebugListViewHolder, position: Int) {
        val model = data[position]
        holder.rootLayout.setBackgroundColor(getItemColor(model.priority))
        holder.tvTitle.text = getText(model)
        holder.rootLayout.setOnClickListener {
            onLogItemClickListener?.onItemClick(position,model)
        }
    }

    private fun getText(model: LogModel): String = when (type) {
        DataType.LOG_CACHE, DataType.CRASH -> model.key
        else -> "${model.createTime} /  ${model.key} /  ${subString(model.message)}"
    }

    override fun getItemCount(): Int = data.size


    private fun subString(text: String): String = if (text.length > 50) {
        text.substring(0, 50)
    } else {
        text
    }

    private fun getItemColor(priority: Int): Int = when (priority) {
        Log.VERBOSE -> 0
        else -> 0
    }

    class DebugListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootLayout = itemView.findViewById<FrameLayout>(R.id.adapter_debug_list_layout)
        val tvTitle = itemView.findViewById<TextView>(R.id.adapter_debug_title_tv)
        val line = itemView.findViewById<View>(R.id.adapter_debug_line_view)
    }

    interface OnLogItemClickListener {
        fun onItemClick(position: Int, logModel: LogModel)
    }
}