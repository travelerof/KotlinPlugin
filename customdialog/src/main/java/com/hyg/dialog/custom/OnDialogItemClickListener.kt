package com.hyg.dialog.custom

import com.hyg.dialog.BaseDialog

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
interface OnDialogItemClickListener {
    fun onItemClick(dialog: BaseDialog, text: String)
}