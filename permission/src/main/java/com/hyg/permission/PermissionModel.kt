package com.hyg.permission

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
data class PermissionModel(val permission: String,@PermissionType val type: Int = PermissionType.NORMAL)