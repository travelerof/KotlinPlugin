package com.hyg.log.core

/**
 * Package:      com.hyg.log
 * ClassName:    IPrinter
 * Author:       hanyonggang
 * Date:         2021/12/23 13:55
 * Description:
 *
 */
internal interface IPrinter {

    /**
     * 是否debug模式
     *
     * @param debug Boolean
     */
    fun debug(debug: Boolean)

    /**
     * 设置输出监听
     *
     * @param printer IPrintOutput
     */
    fun setPrintOutput(printer:IPrintOutput)

    /**
     * 打印 v
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    fun v(tag: String, message: String, throwable: Throwable?)

    /**
     * 打印 d
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    fun d(tag: String, message: String, throwable: Throwable?)

    /**
     * 打印 i
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    fun i(tag: String, message: String, throwable: Throwable?)

    /**
     * 打印 w
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    fun w(tag: String, message: String, throwable: Throwable?)

    /**
     * 打印 e
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    fun e(tag: String, message: String, throwable: Throwable?)

    /**
     * 打印json
     *
     * @param tag String
     * @param json String
     */
    fun json(tag: String,json: String)
}