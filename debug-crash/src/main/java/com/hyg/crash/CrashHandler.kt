package com.hyg.crash

import android.app.Application
import com.hyg.log.data.OnCrashCollectListener
import com.hyg.log.data.manager.DebugDataManager
import java.io.File
import java.io.IOException

/**
 * Package:      com.hyg.crash
 * ClassName:    CrashHandler
 * Author:       hanyonggang
 * Date:         2022/1/4 23:57
 * Description:
 *
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var application: Application? = null
    private var crashListener: OnCollectListener? = null
    private val onCrashCollectListener = object : OnCrashCollectListener {
        override fun onCollect(files: MutableList<File>) {
            crashListener?.onCollect(files)
        }
    }

    fun initialize(application: Application, onCollectListener: OnCollectListener?) {
        this.application = application
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        onCollectListener?.let {
            DebugDataManager.readCrash(onCrashCollectListener)
        }
    }

    fun initialize(application: Application) {
        initialize(application, null)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            DebugDataManager.put(throwable, getDeviceInfo())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        defaultHandler?.let {
            it.uncaughtException(thread, throwable)
        } ?: android.os.Process.killProcess(android.os.Process.myPid())
    }


    private fun getDeviceInfo(): LinkedHashMap<String, String> {
        val params = linkedMapOf<String, String>()
        params["厂商"] = "1"
        params["型号"] = "1"
        params["当前版本"] = "1"
        return params
    }


}