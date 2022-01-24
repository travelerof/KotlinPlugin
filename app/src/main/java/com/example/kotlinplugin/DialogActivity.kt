package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.hyg.dialog.base.BaseDialog
import com.hyg.dialog.base.CommonDialog
import com.hyg.dialog.base.OnDialogClickListener
import com.hyg.dialog.base.TextListener

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
    }

    fun commonDialog(view: View) {

        CommonDialog.Builder(this)
            .title("我是标题")
            .message("我是测试内容我是测试内容我是测试内容我是测试内容我是测试内容我是测试内容")
            .negative("取消",object :OnDialogClickListener{
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }

            })
            .positive("确定",object : OnDialogClickListener{
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }

            })
            .create()
            .show()
    }
}