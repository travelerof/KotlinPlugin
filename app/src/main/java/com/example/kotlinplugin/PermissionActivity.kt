package com.example.kotlinplugin

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hyg.permission.HPermission
import com.hyg.permission.OnRequestPermissionListener

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        val btnPermission = findViewById<Button>(R.id.permission_btn)
        btnPermission.setOnClickListener {
            HPermission.with(this)
                .requestCode(1000)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .listener(object :OnRequestPermissionListener{
                    override fun onSucceed(requestCode: Int) {

                    }

                    override fun onFailed(requestCode: Int, permissions: Array<String>) {

                    }

                })
                .request()
        }
    }
}