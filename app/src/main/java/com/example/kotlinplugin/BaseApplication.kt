package com.example.kotlinplugin

import android.app.Application
import com.hyg.log.ui.LogConfig

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        LogConfig.initialize(this, true)
    }
}