package com.hyg.io

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @Author 韩永刚
 * @Date 2022/01/29
 * @Desc IO操作类
 */
class IOUtils {

    companion object {

        @JvmStatic
        fun write(file:File,bytes:ByteArray){
            val fos = FileOutputStream(file)
            fos.write(bytes)
            fos.flush()
            fos.close()
        }

        @JvmStatic
        fun write(file:File,inputStream: InputStream){

        }
        /**
         * 复制文件
         *
         * @param sourceFile File 源文件
         * @param targetFile File 目标文件
         * @return Boolean
         */
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