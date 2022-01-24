package com.hyg.permission.execute

import android.content.pm.PackageManager
import com.hyg.permission.PermissionOptions
import com.hyg.permission.PermissionType

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
class DefaultExecutor(options: PermissionOptions): AbstractExecutor(options) {

    override fun checkPermission(permission: String): Boolean = options.context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun getType(): Int = PermissionType.NORMAL
}