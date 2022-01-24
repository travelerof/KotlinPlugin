package com.hyg.widget.calendar.delegate

import android.view.View

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    IDelegate
 * Author:       hanyonggang
 * Date:         2022/1/14 14:47
 * Description:
 *
 */
interface IDelegate {

    fun onHandle(what: Int)

    fun <T : View> getViewById(id: Int): T
}