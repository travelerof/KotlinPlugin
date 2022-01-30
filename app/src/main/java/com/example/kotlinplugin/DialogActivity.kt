package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hyg.dialog.BaseDialog
import com.hyg.dialog.common.CommonDialog
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.common.CommonBottomDialog
import com.hyg.dialog.custom.BottomSelectDialog
import com.hyg.dialog.custom.LoadingDialog
import com.hyg.dialog.custom.OnDialogItemClickListener

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
    }

    fun commonDialog(view: View) {

        CommonDialog.Builder(this)
            .title("我是标题")
            .message("我是测试内容我是测试内容我是测试内容我是测试内容我是测试内容我是测试内容")
            .negative("取消",object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }

            })
            .positive("确定",object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }

            })
            .create()
            .show()
    }

    fun bottomSelectDialog(view: View) {
        val data = mutableListOf(
            "选择1",
            "选择2",
            "选择3",
            "选择4"
        )
        BottomSelectDialog.Builder(this)
            .itemData(data)
            .itemListener(object :OnDialogItemClickListener{
                override fun onItemClick(dialog: BaseDialog, text: String) {

                }
            })
            .create()
            .show()
    }

    fun bottomCommpnDialog(view: View) {
        CommonBottomDialog.Builder(this)
            .title("哈哈哈哈")
            .message("大师傅拉萨大家发发简历上空的飞机哦微软sdfklsdjflsk收到附件两款手机访问人")
            .negative("取消",object :OnDialogClickListener{
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }
            })
            .positive("确定",object :OnDialogClickListener{
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }
            })
            .create()
            .show()
    }

    fun loadingDialog(view: View) {
        val dialog = LoadingDialog(this)
        dialog.show()
    }
}