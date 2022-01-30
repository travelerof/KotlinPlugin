package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import com.hyg.io.IOUtils
import java.io.File

class FileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        val btnWrite = findViewById<Button>(R.id.file_write_btn)
        btnWrite.setOnClickListener {
            Thread{

            }.start()
        }
    }
}