package com.hyg.log.core

/**
 * Package:      com.hyg.log
 * ClassName:    HLog
 * Author:       hanyonggang
 * Date:         2021/12/23 13:34
 * Description: 日志输出类
 *
 */
class HLog {

    companion object {
        private const val TAG = "HLog"
        private val printer = LogPrinter()

        /**
         * 设置是否打印日志
         *
         * @param debug Boolean
         */
        @JvmStatic
        fun debug(debug: Boolean) {
            printer.debug(debug)
        }

        @JvmStatic
        fun setPrintOutput(output: IPrintOutput){
            printer.setPrintOutput(output)
        }

        @JvmStatic
        fun v(message: String) {
            v(TAG, message)
        }

        @JvmStatic
        fun v(tag: String, message: String) {
            v(tag, message, null)
        }

        @JvmStatic
        fun v(tag: String, message: String, throwable: Throwable?) {
            printer.v(tag, message, throwable)
        }



        @JvmStatic
        fun i(message: String) {
            i(TAG, message)
        }

        @JvmStatic
        fun i(tag: String, message: String) {
            i(tag, message, null)
        }

        @JvmStatic
        fun i(tag: String, message: String, throwable: Throwable?) {
            printer.i(tag, message, throwable)
        }



        @JvmStatic
        fun d(message: String) {
            d(TAG, message)
        }

        @JvmStatic
        fun d(tag: String, message: String) {
            d(tag, message, null)
        }

        @JvmStatic
        fun d(tag: String, message: String, throwable: Throwable?) {
            printer.d(tag, message, throwable)
        }



        @JvmStatic
        fun w(message: String) {
            w(TAG, message)
        }

        @JvmStatic
        fun w(tag: String, message: String) {
            w(tag, message, null)
        }

        @JvmStatic
        fun w(tag: String, message: String, throwable: Throwable?) {
            printer.w(tag, message, throwable)
        }


        @JvmStatic
        fun e(message: String) {
            e(TAG, message)
        }

        @JvmStatic
        fun e(tag: String, message: String) {
            e(tag, message, null)
        }

        @JvmStatic
        fun e(tag: String, message: String, throwable: Throwable?) {
            printer.e(tag, message, throwable)
        }
    }
}