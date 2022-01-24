package com.hyg.log.data

/**
 * Package:      com.hyg.log.data
 * ClassName:    OnReadFileListener
 * Author:       hanyonggang
 * Date:         2022/1/5 21:20
 * Description:
 *
 */
interface ReadFileCallback {

    fun onResult(filePath: String, text: String)

    fun copyFile(targetPath: String)
}