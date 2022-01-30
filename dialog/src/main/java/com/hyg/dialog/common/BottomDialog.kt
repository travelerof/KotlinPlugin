package com.hyg.dialog.common

import android.content.Context
import android.view.Gravity
import com.hyg.dialog.BaseDialog
import com.hyg.dialog.R

/**
 * Package:      com.hyg.dialog.base
 * ClassName:    BottomDialog
 * Author:       hanyonggang
 * Date:         2021/12/23 19:15
 * Description: 底部弹框基类
 *
 */
open class BottomDialog(context: Context) : BaseDialog(context, R.style.DefaultDialog) {
    override fun gravity(): Int  = Gravity.BOTTOM
    override fun animation(): Int = R.style.BottomAnimation
}