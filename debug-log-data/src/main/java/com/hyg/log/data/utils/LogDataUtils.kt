package com.hyg.log.data.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Package:      com.hyg.log.data.utils
 * ClassName:    HLogUtils
 * Author:       hanyonggang
 * Date:         2022/1/2 11:30
 * Description:
 *
 */
class LogDataUtils {

    companion object {

        const val DEFAULT_STYLE = "yyyy-MM-dd HH:mm:ss"

        @JvmStatic
        fun nowTime(): String = getDateFormat(DEFAULT_STYLE).format(Date())

        @JvmStatic
        fun getDateFormat(style: String): SimpleDateFormat = SimpleDateFormat(style)
    }
}