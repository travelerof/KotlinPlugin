package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ShadowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shadow)
        val tt = "000000455"
        Log.i("ddd","==========${MyTest().test()}=========")
    }
}