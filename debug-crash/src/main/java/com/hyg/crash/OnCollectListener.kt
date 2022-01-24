package com.hyg.crash

import java.io.File

/**
 * Package:      com.hyg.crash
 * ClassName:    CollectCrash
 * Author:       hanyonggang
 * Date:         2022/1/5 10:38
 * Description:
 *
 */
interface OnCollectListener {
    fun onCollect(files: MutableList<File>)
}