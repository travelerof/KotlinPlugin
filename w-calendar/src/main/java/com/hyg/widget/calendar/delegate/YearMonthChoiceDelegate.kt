package com.hyg.widget.calendar.delegate

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.hyg.widget.calendar.ICalendarView
import com.hyg.widget.calendar.R
import com.hyg.widget.calendar.YearChoiceBgLayout
import com.hyg.widget.calendar.adapter.OnAdapterItemClickListener
import com.hyg.widget.calendar.adapter.YearMonthChoiceAdapter
import com.hyg.widget.calendar.annotation.ChoiceType
import com.hyg.widget.calendar.annotation.HandleWhat
import com.hyg.widget.calendar.annotation.Week
import com.hyg.widget.calendar.entity.ChoiceEntity
import com.hyg.widget.calendar.utils.CalendarUtils

/**
 * Package:      com.hyg.widget.calendar.delegate
 * ClassName:    YearMonthChoiceDelegate
 * Author:       hanyonggang
 * Date:         2022/1/16 0:03
 * Description:
 *
 */
class YearMonthChoiceDelegate(context: Context, calendarView: ICalendarView) :
    BaseDelegate(context, calendarView) {


    private val rootContainer = getViewById<FrameLayout>(R.id.year_month_choice_root_layout)
    private val bgView = getViewById<View>(R.id.year_month_choice_bg)
    private val choiceBgLayout = getViewById<YearChoiceBgLayout>(R.id.year_month_choice_layout)
    private val rv = getViewById<RecyclerView>(R.id.year_month_choice_rv)

    private val data = mutableListOf<ChoiceEntity>()

    private val adapter = YearMonthChoiceAdapter(context, data)

    private var choiceType = ChoiceType.YEAR

    private var isAnim = false

    init {
        rv.adapter = adapter
        adapter.setOnAdapterItemClickListener(object : OnAdapterItemClickListener<ChoiceEntity> {
            override fun onItemClick(entity: ChoiceEntity, position: Int) {
                if (choiceType == ChoiceType.MONTH) {
                    getParams().currentMonth = entity.id
                } else {
                    getParams().currentYear = entity.id
                }
                data.forEach {
                    it.selector = it.id == entity.id
                }
                adapter.notifyDataSetChanged()
                calendarView.onHandle(HandleWhat.YEAR_MONTH_CHOICE_UPDATE)
                if (choiceType == ChoiceType.MONTH) {
                    calendarView.onHandle(HandleWhat.UPDATE_MONTH)
                    calendarView.onHandle(HandleWhat.END_MONTH_CHANGED_ANIM)
                } else {
                    calendarView.onHandle(HandleWhat.UPDATE_YEAR)
                    calendarView.onHandle(HandleWhat.END_YEAR_CHANGED_ANIM)
                }
                hideAnim()
            }

        })
        bgView.setOnClickListener {
            calendarView.onHandle(if (choiceType == ChoiceType.MONTH) HandleWhat.END_MONTH_CHANGED_ANIM else HandleWhat.END_YEAR_CHANGED_ANIM)
            hideAnim()
        }
    }


    fun setPercent(percent: Float) {
        choiceBgLayout.setPercent(percent)
    }

    fun isShowChoiceView(): Boolean = rootContainer.visibility == View.VISIBLE

    override fun onHandle(what: Int) {
        super.onHandle(what)
        if (isPause()) {
            return
        }
        when (what) {
            HandleWhat.SHOW_CHOICE_VIEW -> showAnim()
            HandleWhat.HIDE_CHOICE_VIEW -> hideAnim()
            HandleWhat.FIND_YEAR -> {
                choiceType = ChoiceType.YEAR
                updateEntities()
            }
            HandleWhat.FIND_MONTH -> {
                choiceType = ChoiceType.MONTH
                updateEntities()
            }
            else -> {

            }
        }
    }

    private fun updateEntities() {
        val data = if (choiceType == ChoiceType.MONTH) {
            getChoiceEntities(1, 12, getParams().currentMonth)
        } else {
            getChoiceEntities(getParams().minYear, getParams().maxYear, getParams().currentYear)
        }
        this.data.clear()
        this.data += data
        rv.scrollToPosition(getCurrentPosition())
        adapter.notifyDataSetChanged()
    }

    private fun getYearEntities(): MutableList<ChoiceEntity> =
        getChoiceEntities(getParams().minYear, getParams().maxYear, getParams().currentYear)

    private fun getMonthEntities(): MutableList<ChoiceEntity> =
        getChoiceEntities(1, 12, getParams().currentMonth)

    private fun getChoiceEntities(start: Int, end: Int, current: Int): MutableList<ChoiceEntity> {
        if (start > end) {
            return mutableListOf()
        }
        val entities = mutableListOf<ChoiceEntity>()
        for (index in start..end) {
            val entity = ChoiceEntity()
            entity.id = index
            entity.description = "$index"
            entity.selector = current == index
            entities += entity
        }
        return entities
    }

    private fun showAnim() {
        if (isAnim) {
            return
        }
        rootContainer.visibility = View.VISIBLE
        val height = choiceBgLayout.height
        val animator = ValueAnimator.ofFloat(-1f, 0f)
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            choiceBgLayout.translationY = height * value
            bgView.alpha = 1f + value
        }
        animator.duration = 200
        animator.interpolator = LinearInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                animator.cancel()
                isAnim = false
            }
        })
        animator.start()
        isAnim = true
    }

    private fun hideAnim() {
        if (isAnim) {
            return
        }
        rootContainer.visibility = View.VISIBLE
        val height = choiceBgLayout.height
        val animator = ValueAnimator.ofFloat(0f, -1f)
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            choiceBgLayout.translationY = height * value
            bgView.alpha = value + 1f
        }
        animator.duration = 200
        animator.interpolator = LinearInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                animator.cancel()
                data.clear()
                adapter.notifyDataSetChanged()
                rootContainer.visibility = View.GONE
                isAnim = false
            }
        })
        animator.start()
        isAnim = true
    }

    private fun getCurrentPosition(): Int = if (choiceType == ChoiceType.MONTH) {
        getParams().currentMonth - 1
    } else {
        getParams().currentYear - getParams().minYear
    }

    fun getChoiceType(): Int = choiceType
}