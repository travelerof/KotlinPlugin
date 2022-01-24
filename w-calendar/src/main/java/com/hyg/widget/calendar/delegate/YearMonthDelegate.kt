package com.hyg.widget.calendar.delegate

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hyg.widget.calendar.ICalendarView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.annotation.ChoiceType
import com.hyg.widget.calendar.annotation.HandleWhat

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    YearMonthDelegate
 * Author:       hanyonggang
 * Date:         2022/1/14 17:14
 * Description:
 *
 */
class YearMonthDelegate(context: Context, calendarView: ICalendarView) :
    BaseDelegate(context, calendarView) {

    private val rootYearMonthContainer = getViewById<LinearLayout>(R.id.year_month_root_layout)

    private val yearContainer = getViewById<LinearLayout>(R.id.year_layout)

    private val tvYear = getViewById<TextView>(R.id.year_tv)

    private val ivYear = getViewById<ImageView>(R.id.year_changed_iv)

    private val tvYearMeter = getViewById<TextView>(R.id.year_meter_tv)

    private val monthContainer = getViewById<LinearLayout>(R.id.month_layout)

    private val tvMonth = getViewById<TextView>(R.id.month_tv)

    private val ivMonth = getViewById<ImageView>(R.id.month_changed_iv)

    private val tvMonthMeter = getViewById<TextView>(R.id.month_meter_tv)

    private val line = getViewById<View>(R.id.year_month_line_view)

    private var isAnim = false
    init {
        line.visibility = if (getParams().showYearLine) View.VISIBLE else View.GONE
        yearContainer.setOnClickListener {
            if (calendarView.isShowChoiceView()) {
                if (calendarView.getChoiceType() == ChoiceType.MONTH) {
                    hideAnim(ivMonth)
                }else{
                    hideAnim(ivYear)
                }
                calendarView.onHandle(HandleWhat.HIDE_CHOICE_VIEW)
            }else{
                calendarView.showChoiceView(getYearPercent(),ChoiceType.YEAR)
                startAnim(ivYear)
            }
        }
        monthContainer.setOnClickListener {
            if (calendarView.isShowChoiceView()) {
                if (calendarView.getChoiceType() == ChoiceType.MONTH) {
                    hideAnim(ivMonth)
                }else{
                    hideAnim(ivYear)
                }

                calendarView.onHandle(HandleWhat.HIDE_CHOICE_VIEW)
            }else{
                startAnim(ivMonth)
                calendarView.showChoiceView(getMonthPercent(),ChoiceType.MONTH)
            }
        }
    }

    override fun onHandle(what: Int) {
        if (isPause()) {
            return
        }
        when (what) {
            HandleWhat.END_YEAR_CHANGED_ANIM -> hideAnim(ivYear)
            HandleWhat.END_MONTH_CHANGED_ANIM -> hideAnim(ivMonth)
            HandleWhat.UPDATE_YEAR -> tvYear.text = "${getParams().currentYear}"
            HandleWhat.UPDATE_MONTH -> tvMonth.text = "${getParams().currentMonth}"
            else -> {}
        }
    }

    private fun startAnim(view:View){
        if (isAnim) {
            return
        }
        val animator = ValueAnimator.ofFloat(0f,180f)
        animator.duration = 200
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            view.rotation = value
        }
        animator.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                isAnim = false
                animator.cancel()
            }
        })
        animator.start()
        isAnim = true
    }

    private fun hideAnim(view:View){
        if (isAnim) {
            return
        }
        val animator = ValueAnimator.ofFloat(180f,0f)
        animator.duration = 200
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            view.rotation = value
        }
        animator.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                isAnim = false
                animator.cancel()
            }
        })
        animator.start()
        isAnim = true
    }

    private fun getYearPercent(): Float {
        val location = IntArray(2)
        tvYear.getLocationOnScreen(location)
        return (location[0]+tvYear.width/2)/rootYearMonthContainer.width.toFloat()
    }

    private fun getMonthPercent(): Float {
        val location = IntArray(2)
        tvMonth.getLocationOnScreen(location)
        return (location[0]+tvMonth.width/2)/rootYearMonthContainer.width.toFloat()
    }

}