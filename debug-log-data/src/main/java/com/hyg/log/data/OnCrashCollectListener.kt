package com.hyg.log.data

import java.io.File

/**
 * Package:      com.hyg.log.data
 * ClassName:    OnCrashCollectListener
 * Author:       hanyonggang
 * Date:         2022/1/5 10:52
 * Description:
 *
 */
interface OnCrashCollectListener {

    fun onCollect(files: MutableList<File>)
}