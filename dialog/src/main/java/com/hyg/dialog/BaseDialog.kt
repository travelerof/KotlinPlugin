package com.hyg.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager

/**
 * Package:      com.hyg.dialog
 * ClassName:    BaseDialog
 * Author:       hanyonggang
 * Date:         2021/12/23 15:39
 * Description:
 *
 */
open class BaseDialog(context:Context,themeId:Int):Dialog(context,themeId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
    }

    private fun initParams(){
        window?.let {
            val params = it.attributes
            params.gravity = gravity()
            params.width = width()
            params.height = height()
            params.alpha = alpha()
            params.x = offSetX()
            params.y = offSetY()
            it.attributes = params
            it.setWindowAnimations(animation())
        }
    }

    open fun animation():Int = R.style.CenterAnimation

    open fun gravity(): Int = Gravity.CENTER

    open fun offSetX(): Int = 0

    open fun offSetY(): Int = 0

    open fun width(): Int = 0

    open fun height(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun alpha():Float = 1.0f

}