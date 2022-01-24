package com.hyg.log.data

import androidx.annotation.IntDef

/**
 * Package:      com.hyg.log.data
 * ClassName:    DataType
 * Author:       hanyonggang
 * Date:         2022/1/3 12:41
 * Description:
 *
 */
@IntDef(
    DataType.LOG,
    DataType.LOG_CACHE,
    DataType.CRASH
)
@Retention(AnnotationRetention.SOURCE)
annotation class DataType {

    companion object{
        const val LOG = 0
        const val LOG_CACHE = 1
        const val CRASH = 2
    }
}