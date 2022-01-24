package com.hyg.permission

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
annotation class PermissionType{
    companion object{
        /**
         *  正常权限
         */
        const val NORMAL = 0

        /**
         * 特殊权限
         */
        const val SPECIAL = 1
    }
}
