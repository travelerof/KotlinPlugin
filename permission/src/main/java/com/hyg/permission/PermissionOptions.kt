package com.hyg.permission

import android.content.Context

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
class PermissionOptions(val context: Context){
    var requestCode: Int = 0
    val permissions = mutableListOf<String>()
    var onRequestPermissionListener: OnRequestPermissionListener? = null
}