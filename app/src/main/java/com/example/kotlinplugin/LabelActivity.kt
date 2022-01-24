package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hyg.widget.label.LabelView
import com.hyg.widget.label.OnLabelClickListener

class LabelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_label)
        val etSearch = findViewById<EditText>(R.id.label_search_et)
        val btnAdd = findViewById<Button>(R.id.label_add_btn)
        val lable = findViewById<LabelView>(R.id.label_view)
        btnAdd.setOnClickListener {
            lable.addLabel(etSearch.text.toString())
        }
        lable.setOnLabelClickListener(object :OnLabelClickListener{
            override fun onLabelClick(text: String) {
                Toast.makeText(this@LabelActivity,text,Toast.LENGTH_LONG).show()
            }

        })
    }
}