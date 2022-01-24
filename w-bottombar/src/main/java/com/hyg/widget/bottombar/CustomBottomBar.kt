package com.hyg.widget.bottombar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

/**
 * @Author 韩永刚
 * @Date 2021/11/18
 * @Desc
 */
class CustomBottomBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {
    /**
     * item集合
     */
    private val data = mutableListOf<IBottomItemView>()

    /**
     * 当前选中的item角标
     */
    private var currentPosition = 0

    private var underLine = false
    private var textSize = 12.0f
    private var unSelectedTextColor = Color.BLACK
    private var selectedTextColor = Color.RED

    private var vp: ViewPager? = null
    private var vp2: ViewPager2? = null
    /**
     * item参数
     */
    private val iParams = object : IParams {
        override fun textSize(): Float = textSize

        override fun unSelectedTextColor(): Int = unSelectedTextColor

        override fun selectedTextColor(): Int = selectedTextColor
    }

    /**
     *
     * item点击监听
     */
    private val onBottomItemClickListener = object : OnBottomItemClickListener {
        override fun onBottomItemClick(view: IBottomItemView, position: Int) {
            data[currentPosition].setChecked(false)
            currentPosition = position
            view.setChecked(true)
            vp?.currentItem = currentPosition
            vp2?.currentItem = currentPosition
        }

    }

    /**
     * 创建item监听
     */
    private var onCreateBottomItemListener: OnCreateBottomItemListener =
        DefaultCreateBottomItemAdapter()

    init {
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomBottomBar)
        try {
            textSize = typedArray.getDimension(R.styleable.CustomBottomBar_android_textSize,12.0f)
            unSelectedTextColor = typedArray.getColor(R.styleable.CustomBottomBar_unSelectedTextColor,Color.BLACK)
            selectedTextColor = typedArray.getColor(R.styleable.CustomBottomBar_selectedTextColor,Color.RED)
        }catch (e: Exception){

        }finally {
            typedArray.recycle()
        }
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    fun setOnCreateBottomItemListener(onCreateBottomItemListener: OnCreateBottomItemListener) {
        this.onCreateBottomItemListener = onCreateBottomItemListener
    }

    fun setBottomItemEntities(entities: MutableList<CustomBottomEntity>) {
        addItemEntities(entities)
    }

    fun getItemCount(): Int = data.size

    fun getCurrentPosition(): Int = currentPosition

    fun setCurrentPosition(position: Int) {
        if (position in 0 until data.size) {
            data[currentPosition].setChecked(false)
            currentPosition = position
            data[currentPosition].setChecked(true)
        }
    }

    /**
     * 与ViewPager滑动绑定
     * @param vp ViewPager
     */
    fun bindViewPager(vp:ViewPager){
        this.vp = vp
        this.vp?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                setCurrentPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    /**
     * 与viewpager2滑动选中绑定
     *
     * @param vp ViewPager2
     */
    fun bindViewPager2(vp: ViewPager2){
        this.vp2 = vp
        this.vp2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                setCurrentPosition(position)
            }
        })
    }

    private fun addItemEntities(entities: MutableList<CustomBottomEntity>) {
        data.clear()
        removeAllViews()
        val params = LayoutParams(0, LayoutParams.MATCH_PARENT)
        params.weight = 1.0f
        for (position in 0 until entities.size) {
            val iView =
                onCreateBottomItemListener.onCreateItemView(context, iParams, entities[position])
            iView.setId(position)
            iView.setOnBottomItemClickListener(onBottomItemClickListener)
            addView(iView.getView(), params)
            iView.setChecked(currentPosition == position)
            data += iView
        }
    }

    private class DefaultCreateBottomItemAdapter : OnCreateBottomItemListener {
        override fun onCreateItemView(
            context: Context,
            params: IParams,
            entity: CustomBottomEntity
        ): IBottomItemView = CustomBottomItemView(context, params, entity)
    }

}
