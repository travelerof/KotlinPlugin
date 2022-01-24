package com.hyg.permission

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hyg.permission.execute.DefaultExecutor
import com.hyg.permission.execute.PermissionExecutorManager
import com.hyg.permission.execute.SpecialExecutor

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
class HPermission {

    companion object{
        fun with(activity: FragmentActivity): Builder = with(activity,PermissionType.NORMAL)

        fun with(activity: FragmentActivity,@PermissionType type: Int): Builder = Builder(activity,type)
    }


    class Builder(private val context: Context,@PermissionType private val type: Int) {
        private val options = PermissionOptions(context)

        fun requestCode(requestCode: Int): Builder{
            options.requestCode = requestCode
            return this
        }

        fun permission(permission: String): Builder{
            options.permissions += permission
            return this
        }

        fun permissions(permissions: MutableList<String>): Builder{
            options.permissions += permissions
            return this
        }

        fun permissions(vararg permissions: String): Builder{
            options.permissions += permissions
            return this
        }

        fun listener(listener: OnRequestPermissionListener): Builder{
            options.onRequestPermissionListener = listener
            return this
        }
        fun request(){
            val executor = if (type == PermissionType.SPECIAL) SpecialExecutor(options) else DefaultExecutor(options)
            PermissionExecutorManager.push(executor).execute()
        }
    }
}