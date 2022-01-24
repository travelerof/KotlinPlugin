package com.hyg.permission

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
interface OnRequestPermissionListener {

    fun onSucceed(requestCode: Int)

    fun onFailed(requestCode: Int,permissions: Array<String>)
}