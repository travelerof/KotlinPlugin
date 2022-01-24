package com.hyg.log.data

import com.hyg.log.core.HLog
import com.hyg.log.core.IPrintOutput
import com.hyg.log.data.manager.DebugDataManager

/**
 * Package:      com.hyg.log.data
 * ClassName:    LDataConfig
 * Author:       hanyonggang
 * Date:         2022/1/2 17:16
 * Description:
 *
 */
class LDataConfig {

    companion object {

        @JvmStatic
        fun setPrintOutput(output: IPrintOutput) {
            HLog.setPrintOutput(output)
        }

        @JvmStatic
        fun setCrashCallback(){

        }
        /**
         * 设置日志显示最大值
         * @param max Int
         */
        @JvmStatic
        fun setDataMax(max: Int) {
            DebugDataManager.setMax(max)
        }

        @JvmStatic
        fun setCacheDir(dir: String) {
            DebugDataManager.setCacheDir(dir)
        }

    }
}