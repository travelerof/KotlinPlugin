package com.hyg.permission.execute

import com.hyg.permission.PermissionOptions
import com.hyg.permission.PermissionType

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
class SpecialExecutor(options: PermissionOptions): AbstractExecutor(options) {


    override fun checkPermission(permission: String): Boolean {
        return true
    }

    override fun getType(): Int = PermissionType.SPECIAL
}