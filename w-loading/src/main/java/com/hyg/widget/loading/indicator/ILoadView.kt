package com.hyg.widget.loading.indicator

/**
 * Package:      com.hyg.widget.loading.draw
 * ClassName:    ILoadingView
 * Author:       hanyonggang
 * Date:         2021/12/28 15:39
 * Description:
 *
 */
interface ILoadView {

    /**
     * 绘制
     */
    fun draw()

    fun postDraw()

    fun start()

    fun stop()

    fun isRunning():Boolean

    fun getPaintColor():Int
}