package com.hyg.widget.shadow

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.math.abs
import kotlin.math.max

/**
 * Package:      com.hyg.widget.shadow
 * ClassName:    ShadowLayout
 * Author:       hanyonggang
 * Date:         2021/12/26 19:13
 * Description:
 *
 */
class ShadowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    /**
     * 阴影颜色值
     */
    private var shadowColor: Int = 0

    /**
     * 阴影圆角
     */
    private var shadowRadius: Float = 0f

    /**
     * 阴影横向偏移量,正值向右，负值向左
     */
    private var shadowOffsetX: Float = 0f

    /**
     * 阴影纵向偏移量,正值向下，负值向上
     */
    private var shadowOffsetY: Float = 0f

    /**
     * 阴影区域
     */
    private var hiddenShadowLeft = true
    private var hiddenShadowTop = false
    private var hiddenShadowRight = false
    private var hiddenShadowBottom = false


    private var hiddenRadiusTopLeft = false
    private var hiddenRadiusTopRight = false
    private var hiddenRadiusBottomLeft = false
    private var hiddenRadiusBottomRight = false

    /**
     * 阴影画笔
     */
    private val shadowPaint = Paint()


    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        initShadowPaint()
        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        shadowColor = array.getColor(R.styleable.ShadowLayout_s_shadowColor,resources.getColor(R.color.shadow_default_color))
        shadowRadius = array.getDimension(R.styleable.ShadowLayout_s_shadowRadius,0f)
        shadowOffsetX = array.getDimension(R.styleable.ShadowLayout_s_shadowOffsetX,0f)
        shadowOffsetY = array.getDimension(R.styleable.ShadowLayout_s_shadowOffsetY,0f)
        hiddenShadowLeft = array.getBoolean(R.styleable.ShadowLayout_s_shadowHiddenLeft,false)
        hiddenShadowTop = array.getBoolean(R.styleable.ShadowLayout_s_shadowHiddenTop,false)
        hiddenShadowRight = array.getBoolean(R.styleable.ShadowLayout_s_shadowHiddenRight,false)
        hiddenShadowBottom = array.getBoolean(R.styleable.ShadowLayout_s_shadowHiddenBottom,false)
        array.recycle()

        setPadding()
    }

    private fun initShadowPaint() {
        shadowPaint.isAntiAlias = true
        shadowPaint.style = Paint.Style.FILL
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0 && showShadow()) {
            setShadowDrawable(w, h)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
    /**
     * 设置阴影背景
     *
     * @param width Int
     * @param height Int
     */
    private fun setShadowDrawable(width: Int, height: Int) {
        val bt = createShadowBitmap(
            width,
            height,
            shadowRadius,
            shadowOffsetX,
            shadowOffsetY,
            shadowColor,
            Color.TRANSPARENT
        )
        val drawable = BitmapDrawable(resources, bt)
        background = drawable
    }

    /**
     * 绘制阴影bitmap
     *
     * @param width Int
     * @param height Int
     * @param shadowRadius Float 阴影模糊区域
     * @param dx Float 阴影横向偏移
     * @param dy Float 阴影纵向偏移
     * @param shadowColor Int 阴影颜色
     * @param fillColor Int 阴影填充颜色
     * @return Bitmap
     */
    private fun createShadowBitmap(
        width: Int,
        height: Int,
        shadowRadius: Float,
        dx: Float,
        dy: Float,
        shadowColor: Int,
        fillColor: Int
    ): Bitmap {

        val shadowVagueRadius = getVagueRadius(shadowRadius)
        //创建底色bitmap
        val bt = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bt)
        val shadowRectF = createShadowRect(width, height, shadowVagueRadius, 30f)
        setShadowOffset(shadowRectF, dx, dy)
        shadowPaint.color = fillColor
        shadowPaint.setShadowLayer(shadowVagueRadius, dx, dy, shadowColor)
        canvas.drawRoundRect(shadowRectF, shadowRadius, shadowRadius, shadowPaint)
        return bt
    }

    private fun createShadowRect(
        shadowWidth: Int,
        shadowHeight: Int,
        shadowVagueRadius: Float,
        shadowRadius: Float
    ): RectF {
        val rectF = RectF()
        rectF.left = if (hiddenShadowLeft) {
            0f
        } else {
            shadowRadius
        }
        rectF.top = if (hiddenShadowTop) {
            max(shadowVagueRadius, shadowRadius)/2
        } else {
            shadowRadius
        }
        rectF.right = if (hiddenShadowRight) {
            shadowWidth - max(shadowVagueRadius, shadowRadius)/2
        } else {
            shadowWidth - shadowRadius
        }
        rectF.bottom = if (hiddenShadowBottom) {
            shadowHeight - max(shadowVagueRadius, shadowRadius)/2
        } else {
            shadowHeight - shadowRadius
        }
        return rectF
    }

    private fun setShadowOffset(rectF: RectF, dx: Float, dy: Float) {
        if (dx > 0){
            rectF.left += dx
            rectF.right -= dx
        } else if (dx < 0){
            rectF.left -= abs(dx)
        }
    }

    /**
     * 获取模糊阴影半径
     *
     * @param radius Float
     * @return Float
     */
    private fun getVagueRadius(radius: Float): Float{
        return if (radius <= 0){
            DEFAULT_VAGUE_RADIUS
        }else{
            radius/3*2
        }
    }

    private fun setPadding(){
        if (!showShadow()) {
            return
        }
        val xPadding = shadowRadius+ abs(shadowOffsetX)
        val yPadding = shadowRadius + abs(shadowOffsetY)

        val left = if (hiddenShadowLeft) 0 else xPadding
        val top = if (hiddenShadowTop) 0 else yPadding
        val right = if (hiddenShadowRight) 0 else xPadding
        val bottom = if (hiddenShadowBottom) 0 else yPadding

        setPadding(left.toInt(),top.toInt(),right.toInt(),bottom.toInt())
    }
    /**
     * 是否有阴影
     *
     * @return Boolean
     */
    fun showShadow(): Boolean =
        !hiddenShadowLeft || !hiddenShadowTop || !hiddenShadowRight || !hiddenShadowRight


    companion object{
        /**
         * 默认模糊半径
         */
        private const val DEFAULT_VAGUE_RADIUS = 10F
    }
}