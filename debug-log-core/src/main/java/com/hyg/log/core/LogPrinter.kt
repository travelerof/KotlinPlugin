package com.hyg.log.core

import android.util.Log

/**
 * Package:      com.hyg.log
 * ClassName:    LogPrinter
 * Author:       hanyonggang
 * Date:         2021/12/23 13:58
 * Description:
 *
 */
internal class LogPrinter : IPrinter {
    /**
     * 是否打印
     */
    private var debug = false

    /**
     * 日志输出
     */
    private var outPut: IPrintOutput? = null

    override fun debug(debug: Boolean) {
        this.debug = debug
    }

    override fun setPrintOutput(printer: IPrintOutput) {
        outPut = printer
    }

    override fun v(tag: String, message: String, throwable: Throwable?) {
        print(Log.VERBOSE, tag, message, throwable)
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        print(Log.DEBUG, tag, message, throwable)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        print(Log.INFO, tag, message, throwable)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        print(Log.WARN, tag, message, throwable)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        print(Log.ERROR, tag, message, throwable)
    }

    override fun json(tag: String, json: String) {

    }

    /**
     *
     * @param priority Int 日志打印等级
     * @param tag String
     * @param message String
     * @param throwable Throwable?
     */
    private fun print(priority: Int, tag: String, message: String, throwable: Throwable?) {
        if (!debug) {
            return
        }
        val msg = getMessage(message, throwable)
        outPut?.outPut(priority, tag, message)
        Log.println(priority, tag, msg)
    }


    private fun getMessage(message: String, throwable: Throwable?): String {
        return throwable?.let {
            message + it.message
        } ?: message
    }

}