package com.hyg.permission.execute

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hyg.permission.PermissionType

/**
 * @Author 韩永刚
 * @Date 2021/11/27
 * @Desc
 */
class PermissionRequestFragment : Fragment() {

    companion object {
        private const val TAG = "PermissionRequestFragment"
        private const val REQUEST_CODE = 1000
        private const val SPECIAL_REQUEST_CODE = 1001
        private const val PERMISSION_TYPE_KEY = "permissionType"
        private const val PERMISSION_KEY = "permission"
        fun newInstance(
            @PermissionType type: Int,
            permissions: Array<String>,
            callback: OnRequestCallback
        ): PermissionRequestFragment {
            val fragment = PermissionRequestFragment()
            val bundle = Bundle()
            bundle.putInt(PERMISSION_TYPE_KEY, type)
            bundle.putStringArray(PERMISSION_KEY, permissions)
            fragment.arguments = bundle
            fragment.setRequestCallback(callback)
            return fragment
        }
    }

    private var requestCallback: OnRequestCallback? = null
    private var type: Int = PermissionType.NORMAL
    private var permissions: Array<String> = arrayOf()

    private var specialIndex = 0

    /**
     * 与activity绑定
     * @param activity FragmentActivity
     */
    fun attach(activity: FragmentActivity){
        activity.supportFragmentManager.beginTransaction().add(this, TAG).commitAllowingStateLoss()
    }
    fun detach(){
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            type = it.getInt(PERMISSION_TYPE_KEY, PermissionType.NORMAL)
            permissions = it.getStringArray(PERMISSION_KEY) ?: arrayOf()
            request()
        }
    }

    private fun request() {
        when (type) {
            PermissionType.SPECIAL -> requestSpecialPermission()
            else -> if (permissions.isNotEmpty()) {
                requestPermissions(permissions, REQUEST_CODE)
            }
        }
    }

    private fun requestSpecialPermission() {
        if (specialIndex in 0 until permissions.size) {

        }else{

        }
    }

    fun setRequestCallback(requestCallback: OnRequestCallback) {
        this.requestCallback = requestCallback
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            requestCallback?.onRequestResult(permissions,grantResults)
            detach()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}