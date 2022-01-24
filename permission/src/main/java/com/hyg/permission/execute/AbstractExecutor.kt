package com.hyg.permission.execute

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.fragment.app.FragmentActivity
import com.hyg.permission.PermissionOptions
import com.hyg.permission.PermissionType

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
abstract class AbstractExecutor(val options: PermissionOptions) : IExecutor {

    private val requestCallback = object : OnRequestCallback {
        override fun onRequestResult(permissions: Array<out String>, grantResults: IntArray) {
            handleRequestResult(permissions, grantResults)
        }

    }
    private var executeCallback: IExecuteCallback? = null

    override fun setExecuteCallback(callback: IExecuteCallback) {
        this.executeCallback = callback
    }

    override fun execute() {
        val permissions = getRequestPermissions()
        if (permissions.isEmpty()) {
            options.onRequestPermissionListener?.onSucceed(options.requestCode)
            executeCallback?.onCompleted()
            return
        }
        getActivity(options.context)?.let {
            val fragment = PermissionRequestFragment.newInstance(
                getType(),
                permissions.toTypedArray(),
                requestCallback
            )
            fragment.attach(it)
        }
    }

    private fun getRequestPermissions(): MutableList<String> {
        val permissions = mutableListOf<String>()
        options.permissions.forEach {
            if (!checkPermission(it)) {
                permissions += it
            }
        }
        return permissions
    }

    private fun handleRequestResult(permissions: Array<out String>, grantResults: IntArray) {
        val failed = mutableListOf<String>()
        for (index in grantResults.indices){
            if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                failed += permissions[index]
            }
        }
        if (failed.isEmpty()) {
            options.onRequestPermissionListener?.onSucceed(options.requestCode)
        }else{
            options.onRequestPermissionListener?.onFailed(options.requestCode,failed.toTypedArray())
        }
        executeCallback?.onCompleted()
    }

    /**
     * 获取activity
     *
     * @param context Context?
     * @return FragmentActivity?
     */
    private fun getActivity(context: Context?): FragmentActivity? = when (context) {
        is FragmentActivity -> context
        is ContextWrapper -> getActivity(context.baseContext)
        else -> null
    }

    protected abstract fun checkPermission(permission: String): Boolean

    @PermissionType
    protected abstract fun getType(): Int

}