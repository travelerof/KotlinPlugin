package com.example.kotlinplugin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinplugin.test.Test1
import com.google.gson.Gson
import com.hyg.log.core.HLog
import com.hyg.log.ui.DebugListActivity
import com.hyg.log.ui.LogConfig
import java.util.*

class LogActivity : AppCompatActivity() {

    private val handler = Handler() {
        if (it.what == 1000) {
            HLog.v("tttt", "==========11111====sdafas=========")
            HLog.w("tttt", "sdfwer")
            HLog.d("tttt", "===sdfastgdsadf========")
            HLog.i("tttt", "=哈瞬间大幅拉升=")
            HLog.e("tttt", "=46575文人14")
            send()
            true
        } else {
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        LogConfig.initialize(application, true)
        val btnPrint = findViewById<Button>(R.id.log_print_btn)
        val btnClick = findViewById<Button>(R.id.log_click_btn)
        btnPrint.setOnClickListener {
            handler.sendEmptyMessageDelayed(1000, 2000)
        }
        btnClick.setOnClickListener {
            startActivity(Intent(this, DebugListActivity::class.java))
        }
    }

    private lateinit var tt: String
    private fun send(){
//        handler.sendEmptyMessageDelayed(1000, 1000)
//        tt.toString()
        val calendar = Calendar.getInstance()
        Log.i("tesdd","==========${Gson().toJson(Test1())}========")
        //默认
    }
}