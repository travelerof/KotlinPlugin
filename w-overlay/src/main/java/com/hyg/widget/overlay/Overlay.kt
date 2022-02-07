package com.hyg.widget.overlay

import android.app.Application
import android.os.Build
import android.view.WindowManager
import android.widget.PopupWindow

/**
 * @Author 韩永刚
 * @Date 2022/02/07
 * @Desc
 */
class Overlay(private val application: Application) {

    private val layoutParams = WindowManager.LayoutParams()

    init {

    }

    private fun initLayoutParams() {
        layoutParams.type = getLayoutParamsType()
        val p = PopupWindow(application)
    }

    private fun getLayoutParamsType(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    } else {
        WindowManager.LayoutParams.TYPE_PHONE
    }
}