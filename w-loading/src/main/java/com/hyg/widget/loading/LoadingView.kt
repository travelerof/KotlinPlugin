package com.hyg.widget.loading

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.hyg.widget.loading.indicator.*

/**
 * Package:      com.hyg.widget.loading
 * ClassName:    LoadingView
 * Author:       hanyonggang
 * Date:         2021/12/27 19:26
 * Description:
 *
 */
class LoadingView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr),ILoadView {

    private var indicatorId: Int = IndicatorId.LINE_SPIN
    private var color:Int = 0
    private lateinit var draw: AbstractIndicator

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        initAttrs(attrs)
        initIndicatorDraw()
    }

    private fun initAttrs(attrs: AttributeSet?){
        val array = context.obtainStyledAttributes(attrs,R.styleable.LoadingView)
        try {
            color = array.getColor(R.styleable.LoadingView_s_loadColor,resources.getColor(R.color.load_default_color))
            indicatorId = array.getInt(R.styleable.LoadingView_s_indicator, IndicatorId.LINE_SPIN)
        }finally {
            array.recycle()
        }
    }

    private fun initIndicatorDraw(){
        draw = IndicatorFactory.create(indicatorId,this)
        draw.initPaint().color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        draw.onMeasure(measuredWidth,measuredHeight)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            draw.onDraw(it)
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            start()
        }else{
            stop()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (visibility == VISIBLE) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun draw() {
        invalidate()
    }

    override fun postDraw() {
        postInvalidate()
    }

    override fun start() {
        draw.start()
    }

    override fun stop() {
        draw.stop()
    }

    fun setIndicatorId(@IndicatorId indicatorId:Int){
        this.indicatorId = indicatorId
        draw = IndicatorFactory.create(indicatorId,this)
        draw.initPaint().color = color
        start()

    }
    override fun isRunning(): Boolean {
        return false
    }

    override fun getPaintColor(): Int = color
}