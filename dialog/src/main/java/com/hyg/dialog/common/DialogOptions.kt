package com.hyg.dialog.common

import android.view.Gravity
import android.view.View
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.R
import com.hyg.dialog.TextListener

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
internal class DialogOptions {
    /**
     * dialog主题
     */
    var style: Int = R.style.DefaultDialog

    /**
     * dialog宽高
     */
    var width: Int = 0
    var height: Int = 0

    /**
     *
     */
    var alpha: Float = 1.0f
    var gravity: Int = Gravity.CENTER
    var windowAnimation: Int = R.style.CenterAnimation
    var canOutside: Boolean = true
    var cancelable:Boolean = true
    var title: String = ""
    var showLine: Boolean = true
    var message: String = ""
    var negativeText: String = ""
    var positiveText: String = ""
    var contentView: View? = null
    var titleListener: TextListener? = null
    var messageListener: TextListener? = null
    var negativeTextListener: TextListener? = null
    var positiveTextListener: TextListener? = null
    var negativeClickListener: OnDialogClickListener? = null
    var positiveClickListener: OnDialogClickListener? = null


    fun hasMessage(): Boolean = message != "" || contentView != null

    fun hasBottomButton(): Boolean = negativeText != "" || positiveText != ""
}