package com.hyg.widget.bottombar

import android.content.Context

/**
 * @Author 韩永刚
 * @Date 2021/11/26
 * @Desc
 */
interface OnCreateBottomItemListener {
    fun onCreateItemView(context: Context,params: IParams, entity: CustomBottomEntity): IBottomItemView
}