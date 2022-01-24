package com.hyg.widget.bottombar

import android.view.View

/**
 * @Author 韩永刚
 * @Date 2021/11/26
 * @Desc
 */
interface IBottomItemView {
    /**
     * 获取根view
     *
     * @return View
     */
    fun getView(): View

    /**
     * 设置选中
     *
     * @param checked Boolean
     */
    fun setChecked(checked: Boolean)

    /**
     * 判断当前item是否选中
     * @return Boolean
     */
    fun isChecked(): Boolean

    /**
     * item id
     *
     * @param id Int
     */
    fun setId(id: Int)

    /**
     * item监听
     *
     * @param onBottomItemClickListener OnBottomItemClickListener
     */
    fun setOnBottomItemClickListener(onBottomItemClickListener: OnBottomItemClickListener)

    /**
     * 开始执行动画
     *
     * @param up Boolean
     */
    fun startAnimation(up: Boolean)
}