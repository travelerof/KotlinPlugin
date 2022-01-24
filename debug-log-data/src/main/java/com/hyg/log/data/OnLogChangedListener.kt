package com.hyg.log.data

import com.hyg.log.data.model.LogModel

/**
 * Package:      com.hyg.log.data
 * ClassName:    OnLogChangedListener
 * Author:       hanyonggang
 * Date:         2022/1/2 17:45
 * Description:
 *
 */
interface OnLogChangedListener {

    fun onLogChanged(@DataType type: Int, logs: MutableList<LogModel>)
}