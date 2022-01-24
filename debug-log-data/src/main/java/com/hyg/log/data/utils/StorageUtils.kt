package com.hyg.log.data.utils

import java.io.*

/**
 * Package:      com.hyg.log.data.utils
 * ClassName:    StorageUtils
 * Author:       hanyonggang
 * Date:         2022/1/4 18:02
 * Description:
 *
 */
internal class StorageUtils {

    companion object {

        @JvmStatic
        fun write(file: File, text: String) {
            val fos = FileOutputStream(file)
            fos.use {
                it.write(text.toByteArray())
                it.flush()
                it.close()
            }
        }

        @JvmStatic
        fun write(file: File, throwable: Throwable, params: LinkedHashMap<String, String>) {
            val sb = StringBuffer()
            sb.append("\r\n")
                .append(LogDataUtils.nowTime())
                .append("\r\n")
                .append("\r\n")
            params.keys.forEach {
                sb.append("$it :  ${params[it]}\n")
            }
            sb.append("\r\n")
                .append("\r\n")
            val printerWriter = PrintWriter(BufferedWriter(FileWriter(file)))
            printerWriter.append(sb)
            throwable.printStackTrace(printerWriter)
            printerWriter.flush()
            printerWriter.close()
        }

        @JvmStatic
        fun read(file: File): String {
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream()
            var text = ""
            try {
                val buffer = ByteArray(1024)
                var length = 0
                while (true) {
                    length = fis.read(buffer)
                    if (length == -1) {
                        break
                    }
                    bos.write(buffer, 0, length)
                }
                val br = bos.toByteArray()
                text = String(br, 0, br.size)
            } finally {
                fis.close()
                bos.close()
            }
            return text
        }


        @JvmStatic
        fun readDir(file: File): MutableList<File> {
            val data = mutableListOf<File>()
            if (!file.exists() || file.isFile) {
                return data
            }
            file.listFiles()?.let {
                it.forEach { f ->
                    if (f.isFile) {
                        data += f
                    }
                }
            }
            return data
        }

        @JvmStatic
        fun getTargetDir(cacheDir: String, secondary: String): File {
            val file = File(cacheDir, secondary)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        @JvmStatic
        fun createFile(filePath: File, fileName: String): File {
            val file = File(filePath, fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        @JvmStatic
        fun deleteDir(file: File): Boolean {
            if (file.isFile) {
                return false
            }
            file.listFiles()?.let {
                it.forEach { f ->
                    if (f.isFile) {
                        f.delete()
                    }
                }
            }
            return true
        }

        fun copyFile(sourceFile: File, targetFile: File):Boolean {
            val fis = FileInputStream(sourceFile)
            val fos = FileOutputStream(targetFile)
            try {
                val buffer = ByteArray(1024)
                var length = 0
                while (true){
                    length = fis.read(buffer)
                    if (length == -1) {
                        break
                    }
                    fos.write(buffer,0,length)
                }
                fos.flush()
                return true
            } finally {
                fis.close()
                fos.close()
            }
            return false
        }
    }
}