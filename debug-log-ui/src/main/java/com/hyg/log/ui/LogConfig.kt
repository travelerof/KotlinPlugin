package com.hyg.log.ui

import android.app.Application
import com.hyg.crash.CrashHandler
import com.hyg.crash.OnCollectListener
import com.hyg.log.core.HLog
import com.hyg.log.data.DefaultLogPrinter
import com.hyg.log.data.LDataConfig

/**
 * Package:      com.hyg.log.ui
 * ClassName:    LogConfig
 * Author:       hanyonggang
 * Date:         2022/1/4 19:49
 * Description:
 *
 */
class LogConfig {
    companion object {

        @JvmStatic
        fun initialize(application: Application, debug: Boolean) {
            initialize(application, debug, null)
        }

        @JvmStatic
        fun initialize(
            application: Application,
            debug: Boolean,
            onCollectListener: OnCollectListener?
        ) {
            HLog.debug(debug)
            if (debug) {
                LDataConfig.setPrintOutput(DefaultLogPrinter())
                LDataConfig.setCacheDir(application.cacheDir.absolutePath)
            }
            CrashHandler.initialize(application, onCollectListener)
        }
    }
}