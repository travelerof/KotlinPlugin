package com.hyg.io

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @Author 韩永刚
 * @Date 2022/01/29
 * @Desc IO操作类
 */
class IOUtils {

    companion object {

        @JvmStatic
        fun copyFile(sourceFile: File, targetFile: File): Boolean {
            val input = FileInputStream(sourceFile).channel
            val outPut = FileOutputStream(targetFile).channel
            try {
                outPut.transferFrom(input, 0, input.size())
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                input.close()
                outPut.close()
            }
            return false
        }
    }
}