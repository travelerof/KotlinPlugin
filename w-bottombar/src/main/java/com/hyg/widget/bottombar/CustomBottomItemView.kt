package com.hyg.widget.bottombar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @Author 韩永刚
 * @Date 2021/11/26
 * @Desc
 */
class CustomBottomItemView(private val context: Context,private val params: IParams, private val entity: CustomBottomEntity): IBottomItemView {
    private val view = LayoutInflater.from(context).inflate(R.layout.item_bottom_custom_layout,null)

    private val layout = view.findViewById<LinearLayout>(R.id.bottom_bar_item_layout)
    private val iv = view.findViewById<ImageView>(R.id.bottom_bar_item_iv)
    private val tv = view.findViewById<TextView>(R.id.bottom_bar_item_tv)

    private var id: Int = 0
    private var checked = false
    private var onBottomItemClickListener: OnBottomItemClickListener? = null

    init {
        tv.visibility = if ("" != entity.text) {
            tv.text = entity.text
            View.VISIBLE
        }else View.GONE
        view.setOnClickListener {
            onBottomItemClickListener?.onBottomItemClick(this,id)
        }
    }
    override fun getView(): View = view

    override fun setChecked(checked: Boolean) {
        this.checked = checked
        updateCheckedStatus()
    }

    override fun isChecked(): Boolean = checked

    override fun setId(id: Int) {
        this.id = id
    }

    override fun setOnBottomItemClickListener(onBottomItemClickListener: OnBottomItemClickListener) {
        this.onBottomItemClickListener = onBottomItemClickListener
    }

    override fun startAnimation(up: Boolean) {
    }

    private fun updateCheckedStatus(){
        if (isChecked()) {
            tv.setTextColor(params.selectedTextColor())
            iv.setImageResource(entity.selectedIconId)
        }else{
            tv.setTextColor(params.unSelectedTextColor())
            iv.setImageResource(entity.unSelectedIconId)
        }
    }
}