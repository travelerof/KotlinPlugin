package com.hyg.widget.calendar.adapter

/**
 * Package:      com.hyg.widget.calendar.adapter
 * ClassName:    OnAdapterItemClickListener
 * Author:       hanyonggang
 * Date:         2022/1/16 11:26
 * Description:
 *
 */
internal interface OnAdapterItemClickListener<T> {
    fun onItemClick(entity: T, position: Int)
}