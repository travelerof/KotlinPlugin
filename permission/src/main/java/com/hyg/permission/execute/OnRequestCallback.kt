package com.hyg.permission.execute

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
interface OnRequestCallback {

    fun onRequestResult(permissions: Array<out String>,grantResults: IntArray)
}