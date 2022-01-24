package com.hyg.log.data.manager

import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.gson.Gson
import com.hyg.log.data.DataType
import com.hyg.log.data.OnCrashCollectListener
import com.hyg.log.data.OnLogChangedListener
import com.hyg.log.data.ReadFileCallback
import com.hyg.log.data.model.ContentModel
import com.hyg.log.data.model.LogModel
import com.hyg.log.data.utils.LogDataUtils
import com.hyg.log.data.utils.StorageUtils
import java.io.File
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Package:      com.hyg.log.data
 * ClassName:    LogDataManager
 * Author:       hanyonggang
 * Date:         2022/1/2 16:01
 * Description:
 *
 */
object DebugDataManager {

    /**
     * 刷新在线日志
     */
    private const val REFRESH_LOG = 600

    /**
     * 刷新缓存日志
     */
    private const val REFRESH_CACHE = 601

    /**
     * 刷新崩溃日志
     */
    private const val REFRESH_CRASH = 602

    /**
     * 首次读取崩溃日志列表
     */
    private const val READ_CRASH = 603

    /**
     * 读文件内容
     */
    private const val READ_FILE = 604

    /**
     * 清空文件
     */
    private const val CLEAR_FILE = 605

    /**
     * 复制文件
     */
    private const val COPY_FILE = 606


    /**
     * 缓存日志目录
     */
    private const val LOG_CACHE_DIR = "_log"

    /**
     * 崩溃日志目录
     */
    private const val CRASH_DIR = "_crash"

    /**
     * 处理线程池
     */
    private val poolExecutor = ThreadPoolExecutor(
        2,
        5,
        30,
        TimeUnit.SECONDS,
        LinkedBlockingDeque()
    )

    private val handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            REFRESH_LOG -> {
                val logData = it.obj as MutableList<LogModel>
                listeners.forEach { l ->
                    l.onLogChanged(DataType.LOG, logData)
                }
                true
            }
            REFRESH_CACHE -> {
                val logCacheData = it.obj as MutableList<LogModel>
                listeners.forEach { l ->
                    l.onLogChanged(DataType.LOG_CACHE, logCacheData)
                }
                true
            }
            REFRESH_CRASH -> {
                val crashData = it.obj as MutableList<LogModel>
                listeners.forEach { l ->
                    l.onLogChanged(DataType.CRASH, crashData)
                }
                true
            }
            READ_CRASH -> {
                val crashData = it.obj as MutableList<File>
                onCrashCollectListener?.onCollect(crashData)
                true
            }
            READ_FILE -> {
                val content = it.obj as ContentModel
                readCallbacks.forEach { l ->
                    l.onResult(content.filePath, content.text)
                }
                true
            }
            CLEAR_FILE -> {
                query(it.arg1, "")
                true
            }
            COPY_FILE -> {
                val path = it.obj as String
                readCallbacks.forEach { l->
                    l.copyFile(path)
                }
                true
            }
            else -> false
        }
    }

    /**
     * 日志数据变化监听
     */
    private val listeners = mutableListOf<OnLogChangedListener>()
    private val readCallbacks = mutableListOf<ReadFileCallback>()
    private var onCrashCollectListener: OnCrashCollectListener? = null
    private var max: Int = 300

    private var cacheDir: String = ""

    private val logData = mutableListOf<LogModel>()
    private val keywords = mutableListOf<String>()
    private var keyword: String = ""

    fun addListener(listener: OnLogChangedListener) {
        if (listeners.contains(listener)) {
            return
        }
        listeners += listener
    }

    fun removeListener(listener: OnLogChangedListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    fun addReadCallback(callback: ReadFileCallback) {
        if (readCallbacks.contains(callback)) {
            return
        }
        readCallbacks += callback
    }

    fun removeReadCallback(callback: ReadFileCallback) {
        if (readCallbacks.contains(callback)) {
            readCallbacks.remove(callback)
        }
    }

    fun setCacheDir(dir: String) {
        this.cacheDir = dir
    }

    fun setMax(max: Int) {
        DebugDataManager.max = max
    }

    fun put(key: String, message: String?) {
        put(LogModel(key, message))
    }

    fun put(model: LogModel) {
        if (logData.size > max) {
            saveLog()
        }
        logData += model
        query(DataType.LOG, keyword)
    }

    fun put(throwable: Throwable, params: LinkedHashMap<String, String>) {
        val file = StorageUtils.createFile(
            StorageUtils.getTargetDir(cacheDir, CRASH_DIR),
            "crash-${LogDataUtils.nowTime()}.txt"
        )
        StorageUtils.write(file, throwable, params)
    }

    fun readFile(@DataType type: Int, filename: String) {
        if (!isFileQuery()) {
            return
        }
        poolExecutor.execute {
            val file = StorageUtils.getTargetDir(
                cacheDir,
                if (type == DataType.CRASH) CRASH_DIR else LOG_CACHE_DIR
            )

            val text = StorageUtils.read(File(file, filename))
            val msg = Message.obtain()
            msg.what = READ_FILE
            msg.obj = ContentModel(file.absolutePath, text)
            handler.sendMessage(msg)
        }
    }

    fun readCrash(onCrashCollectListener: OnCrashCollectListener?) {
        this.onCrashCollectListener = onCrashCollectListener
        poolExecutor.execute {
            queryCrash(READ_CRASH)
        }
    }

    fun query(@DataType type: Int, keyword: String) {
        if (!isLogQuery()) {
            return
        }
        this.keyword = keyword
        keywords += keyword
        poolExecutor.execute {
            when (type) {
                DataType.LOG -> queryLog()
                DataType.LOG_CACHE -> queryLogCache()
                DataType.CRASH -> queryCrash(REFRESH_CRASH)
                else -> {
                }
            }
        }
    }

    /**
     * 查询在线日志
     */
    private fun queryLog() {
        val filterData = mutableListOf<LogModel>()
        val msg = Message.obtain()
        msg.what = REFRESH_LOG
        if (keyword == "") {
            msg.obj = logData
        } else {
            logData.forEach {
                if (it.key.contains(keyword) || (it.message.contains(keyword))) {
                    filterData += it
                }
            }
            msg.obj = filterData
        }
        handler.sendMessage(msg)
    }

    /**
     * 查询缓存日志
     */
    private fun queryLogCache() {
        val file = StorageUtils.getTargetDir(cacheDir, LOG_CACHE_DIR)
        val cacheFiles = StorageUtils.readDir(file)
        val data = mutableListOf<LogModel>()
        cacheFiles.forEach {
            data += LogModel(it.name, it.absolutePath)
        }
        val msg = Message.obtain()
        msg.obj = data
        msg.what = REFRESH_CACHE
        handler.sendMessage(msg)
    }

    /**
     * 查询崩溃日志
     *
     * @param what Int
     */
    private fun queryCrash(what: Int) {
        val file = StorageUtils.getTargetDir(cacheDir, CRASH_DIR)
        val msg = Message.obtain()
        val crashFiles = StorageUtils.readDir(file)
        if (what == REFRESH_CRASH) {
            val data = mutableListOf<LogModel>()
            crashFiles.forEach {
                data += LogModel(it.name, it.absolutePath)
            }
            msg.obj = data
        } else {
            msg.obj = crashFiles
        }

        msg.what = what
        handler.sendMessage(msg)
    }

    private fun saveLog() {
        poolExecutor.execute {
            val text = Gson().toJson(logData)
            val file = StorageUtils.createFile(
                StorageUtils.getTargetDir(cacheDir, LOG_CACHE_DIR),
                "log-${LogDataUtils.nowTime()}.txt"
            )
            StorageUtils.write(file, text)
            logData.clear()
        }
    }

    private fun isLogQuery(): Boolean = listeners.isNotEmpty()
    private fun isFileQuery(): Boolean = readCallbacks.isNotEmpty()
    fun recycle() {
        logData.clear()
        listeners.clear()
    }

    fun clear(@DataType type: Int) {
        when (type) {
            DataType.LOG_CACHE, DataType.CRASH -> clearFiles(type)
            else -> {
                logData.clear()
                query(DataType.LOG, keyword)
            }
        }
    }

    private fun clearFiles(type: Int) {
        poolExecutor.execute {
            val file = StorageUtils.getTargetDir(
                cacheDir,
                if (type == DataType.LOG_CACHE) LOG_CACHE_DIR else CRASH_DIR
            )
            if (StorageUtils.deleteDir(file)) {
                val msg = Message.obtain()
                msg.arg1 = type
                msg.what = CLEAR_FILE
                handler.sendMessage(msg)
            }
        }
    }

    fun copy(sourceFile: File) {
        poolExecutor.execute {
            val targetFile = File(Environment.getExternalStorageDirectory(),sourceFile.name)
            if (targetFile.exists()) {
                targetFile.delete()
            }
            targetFile.createNewFile()
            if (StorageUtils.copyFile(sourceFile,targetFile)) {
                val msg = Message.obtain()
                msg.what = COPY_FILE
                msg.obj = targetFile.absolutePath
                handler.sendMessage(msg)
            }
        }
    }
}