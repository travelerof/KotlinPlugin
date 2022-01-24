package com.hyg.log.core

import android.util.Log
import androidx.annotation.IntDef

/**
 * Package:      com.hyg.log.core
 * ClassName:    LogPriority
 * Author:       hanyonggang
 * Date:         2022/1/4 14:17
 * Description:
 *
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR)
annotation class LogPriority