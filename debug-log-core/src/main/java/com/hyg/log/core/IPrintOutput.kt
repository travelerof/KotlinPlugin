package com.hyg.log.core

/**
 * Package:      com.hyg.log
 * ClassName:    ILogOutput
 * Author:       hanyonggang
 * Date:         2021/12/23 13:46
 * Description: 日志输出
 *
 */
interface IPrintOutput {

    /**
     *
     * @param priority Int
     * @param tag String
     * @param message String
     */
    fun outPut(@LogPriority priority: Int, tag: String, message: String)
}