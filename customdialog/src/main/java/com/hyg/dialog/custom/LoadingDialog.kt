package com.hyg.dialog.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.hyg.dialog.BaseDialog
import com.hyg.widget.loading.LoadingView
import com.hyg.widget.loading.indicator.IndicatorId

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc loading加载框
 */
class LoadingDialog(
    context: Context,
    @IndicatorId private val indicatorId: Int = IndicatorId.BALL_SPIN
) : BaseDialog(context, R.style.LoadingDialog) {
    private val view = LayoutInflater.from(context).inflate(R.layout.dialog_loadding_layout, null)
    private val loadingView = view.findViewById<LoadingView>(R.id.dialog_loading_view)
    private val tv = view.findViewById<TextView>(R.id.dialog_loading_tv)

    init {
        loadingView.setIndicatorId(indicatorId)
        setContentView(view)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun setText(text:String){
        tv.text = text
    }


    override fun width(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    override fun height(): Int = WindowManager.LayoutParams.WRAP_CONTENT
}