package com.hyg.io

import android.os.Environment
import java.io.File

/**
 * @Author 韩永刚
 * @Date 2022/01/29
 * @Desc 文件操作类
 */
class FileUtils {
    companion object {

        /**
         * 获取sdcard路径
         *
         * @return File
         */
        @JvmStatic
        fun getSDcardDirectory(): File = Environment.getExternalStorageDirectory()

        /**
         * 获取sdcard下载路径
         *
         * @return File
         */
        @JvmStatic
        fun getDownloadDirectory(): File = Environment.getDownloadCacheDirectory()

        /**
         * 创建文件
         *
         * @param path String 文件路径
         * @param name String 文件名
         * @return File
         */
        @JvmStatic
        fun createFile(path: String, name: String): File {
            val file = File(path, name)
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        /**
         * 创建文件
         *
         * @param filePath String 文件路径
         * @return File
         */
        @JvmStatic
        fun createFile(filePath: String): File {
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        /**
         * 获取/创建文件
         *
         * @param path String 文件路径
         * @param name String 文件名
         * @return File
         */
        @JvmStatic
        fun getOrCreateFile(path: String, name: String): File {
            val file = File(path, name)
            if (file.exists()) {
                return file
            }
            return createFile(path, name)
        }

        /**
         * 获取/创建文件
         *
         * @param filePath String 文件路径
         * @return File
         */
        @JvmStatic
        fun getOrCreateFile(filePath: String): File {
            val file = File(filePath)
            if (file.exists()) {
                return file
            }
            return createFile(filePath)
        }


        /**
         * 创建文件夹
         *
         * @param dir String 绝对路径
         * @param targetPath String 需要创建路径
         */
        @JvmStatic
        fun createDirectory(dir: String, targetPath: String): File {
            val file = File(dir, targetPath)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        /**
         * 创建文件夹
         *
         * @param path String 文件路径
         * @return File
         */
        @JvmStatic
        fun createDirectory(path: String): File {
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        /**
         * 文件/文件夹是否存在
         *
         * @param path String 路径
         * @return Boolean
         */
        @JvmStatic
        fun exist(path: String): Boolean = File(path).exists()

        /**
         * 修改文件名
         *
         * @param file File 需要修改的文件
         * @param newName String 新的文件名
         * @return Boolean
         */
        @JvmStatic
        fun rename(file: File, newName: String): Boolean {
            if (!file.exists()) {
                return false
            }
            val newFile = File(file.parentFile, newName)
            return file.renameTo(newFile)
        }

        /**
         * 删除文件夹
         *
         * @param file File
         */
        @JvmStatic
        fun deleteDir(file: File) {
            if (file.isFile) {
                delete(file)
                return
            }
            file.listFiles()?.forEach {
                if (it.isFile) {
                    it.delete()
                } else {
                    deleteDir(it)
                }
            }
            file.delete()
        }

        /**
         * 读当前文件夹下的文件和文件夹
         *
         * @param directory File 文件夹
         * @return MutableList<File>
         */
        @JvmStatic
        fun read(directory: File):MutableList<File>{
            val files = mutableListOf<File>()
            if (directory.isFile) {
                files += directory
                return files
            }
            directory.listFiles()?.forEach {
                files += it
            }
            return files
        }

        /**
         * 读文件夹中的文件
         *
         * @param directory File
         * @param isAll Boolean true表示读取该目录下的所有文件，包括次级子文件夹中的文件,false
         * @return MutableList<File>
         */
        @JvmStatic
        fun readFiles(directory:File,isAll:Boolean):MutableList<File>{
            val files = mutableListOf<File>()
            if (directory.isFile) {
                files += directory
                return files
            }
            directory.listFiles()?.forEach {
                if (it.isFile) {
                    files += it
                } else{
                    if (isAll) {
                        files += readFiles(it,isAll)
                    }
                }
            }
            return files
        }

        /**
         * 删除文件
         *
         * @param file File 文件
         * @return Boolean 是否删除成功
         */
        @JvmStatic
        fun delete(file: File): Boolean {
            if (file.isFile && file.exists()) {
                return file.delete()
            }
            return false
        }

        /**
         * 复制文件
         *
         * @param sourceFile File 源文件
         * @param targetFile File 目标文件
         */
        @JvmStatic
        fun copyFile(sourceFile: File, targetFile: File): Boolean =
            copyOrMoveFile(sourceFile, targetFile, false)

        /**
         * 移动文件
         *
         * @param sourceFile File 源文件
         * @param targetFile File 目标文件
         * @return Boolean
         */
        @JvmStatic
        fun moveFile(sourceFile: File, targetFile: File): Boolean =
            copyOrMoveFile(sourceFile, targetFile, true)

        /**
         * 复制/移动文件
         * @param sourceFile File 源文件
         * @param targetFile File 目标文件
         * @param isMove Boolean 是否移动文件
         * @return Boolean
         */
        @JvmStatic
        fun copyOrMoveFile(sourceFile: File, targetFile: File, isMove: Boolean): Boolean {
            //如果源文件不存在或者源文件是一个文件夹,则不复制
            if (!sourceFile.exists() || sourceFile.isDirectory) {
                return false
            }
            if (!targetFile.exists()) {
                targetFile.createNewFile()
            }
            return IOUtils.copyFile(sourceFile, targetFile) && sourceFile.delete()
        }

        /**
         * 辅助文件夹
         *
         * @param sourceFile File 源文件夹
         * @param targetFile File 目标文件夹
         * @return Boolean 是否复制成功
         */
        @JvmStatic
        fun copyDirectory(sourceFile: File, targetFile: File): Boolean =
            copyMoveDirectory(sourceFile, targetFile, false)

        /**
         * 移动文件夹
         *
         * @param sourceFile File 源文件夹
         * @param targetFile File 目标文件夹
         * @return Boolean 是否移动成功
         */
        @JvmStatic
        fun moveDirectory(sourceFile: File, targetFile: File): Boolean =
            copyMoveDirectory(sourceFile, targetFile, true)

        /**
         * 移动/复制文件夹
         * @param sourceFile File 源文件夹
         * @param targetFile File 目标文件夹
         * @param isMove Boolean 是否移动文件
         * @return Boolean
         */
        @JvmStatic
        fun copyMoveDirectory(sourceFile: File, targetFile: File, isMove: Boolean): Boolean {
            //如果源文件不存在或者源文件是一个文件,则不复制
            if (!sourceFile.exists() || sourceFile.isFile) {
                return false
            }
            if (!targetFile.exists()) {
                targetFile.mkdirs()
            }
            sourceFile.listFiles()?.forEach {
                if (it.isFile) {
                    val newFile = createFile(targetFile.absolutePath, it.name)
                    if (!copyOrMoveFile(it, newFile, isMove)) {
                        return false
                    }
                } else {
                    val newDirectoryFile = createDirectory(targetFile.absolutePath, it.name)
                    if (!copyMoveDirectory(it, newDirectoryFile, isMove)) {
                        return false
                    }
                }
            }
            if (isMove) {
                deleteDir(sourceFile)
            }
            return true
        }
    }
}