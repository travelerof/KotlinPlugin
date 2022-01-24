package com.hyg.log.data

import com.hyg.log.core.IPrintOutput
import com.hyg.log.data.manager.DebugDataManager
import com.hyg.log.data.model.LogModel

/**
 * Package:      com.hyg.log.data
 * ClassName:    LogPrinter
 * Author:       hanyonggang
 * Date:         2022/1/2 17:03
 * Description:
 *
 */
class DefaultLogPrinter:IPrintOutput {
    override fun outPut(priority: Int, tag: String, message: String) {
        DebugDataManager.put(LogModel(tag,message,priority))
    }
}