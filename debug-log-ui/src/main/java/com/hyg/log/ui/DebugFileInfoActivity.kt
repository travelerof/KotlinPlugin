package com.hyg.log.ui

import android.Manifest
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyg.dialog.BaseDialog
import com.hyg.dialog.common.CommonDialog
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.TextListener
import com.hyg.log.data.DataType
import com.hyg.log.data.ReadFileCallback
import com.hyg.log.data.manager.DebugDataManager
import com.hyg.permission.HPermission
import com.hyg.permission.OnRequestPermissionListener
import java.io.File

class DebugFileInfoActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ivHandler: ImageView
    private lateinit var tvInfo: TextView
    private val readCallback = object : ReadFileCallback {
        override fun onResult(filePath: String, text: String) {
            tvInfo.text = text
            this@DebugFileInfoActivity.filePath = filePath
        }

        override fun copyFile(targetPath: String) {
            Toast.makeText(this@DebugFileInfoActivity,targetPath,Toast.LENGTH_SHORT).show()
        }
    }
    private var filePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_file_info)
        val type = intent.getIntExtra("fileType", DataType.LOG_CACHE)
        val filename = intent.getStringExtra("fileName")
        DebugDataManager.addReadCallback(readCallback)
        initView()
        if (filename != null && filename != "") {
            DebugDataManager.readFile(type, filename)
        }
    }

    private fun initView() {
        ivBack = findViewById(R.id.debug_title_back_iv)
        tvTitle = findViewById(R.id.debug_title_info_tv)
        ivHandler = findViewById(R.id.debug_title_handle_iv)
        tvInfo = findViewById(R.id.debug_file_info_tv)
        tvTitle.text = "日志详情"
        ivHandler.setImageResource(R.mipmap.ic_log_export)
        ivBack.setOnClickListener { finish() }
        ivHandler.setOnClickListener { showExportDialog() }
    }

    private fun showExportDialog() {
        CommonDialog.Builder(this)
            .message("是否导出文件?", object : TextListener {
                override fun onText(text: TextView) {
                    text.gravity = Gravity.CENTER
                }
            })
            .negative("取消", object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }
            })
            .positive("导出", object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                    requestPermission()
                }
            })
            .create()
            .show()
    }

    private fun requestPermission() {
        HPermission.with(this)
            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .listener(object :OnRequestPermissionListener{
                override fun onSucceed(requestCode: Int) {
                    if (requestCode == 1010) {
                        export()
                    }
                }

                override fun onFailed(requestCode: Int, permissions: Array<String>) {

                }

            })
            .requestCode(1010)
            .request()
    }

    private fun export(){
        if (filePath == "") {
            return
        }
        DebugDataManager.copy(File(filePath))
    }
    override fun onDestroy() {
        DebugDataManager.removeReadCallback(readCallback)
        super.onDestroy()
    }
}