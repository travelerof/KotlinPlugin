package com.example.kotlinplugin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.main_rv)
        rv.adapter = MainAdapter(this)
    }

    class MainAdapter(private val context: Context):RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

        private val data = mutableListOf<MainData>(
            MainData("dialog",DialogActivity::class.java),
            MainData("shadow",ShadowActivity::class.java),
            MainData("loading",LoadingActivity::class.java),
            MainData("label",LabelActivity::class.java),
            MainData("log",LogActivity::class.java),
            MainData("calendar",CalendarActivity::class.java),
        )
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main,parent,false))
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.tv.text = data[position].description
            holder.tv.setOnClickListener {
                context.startActivity(Intent(context,data[position].clazz))
            }
        }

        override fun getItemCount(): Int  = data.size

        class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val tv = itemView.findViewById<TextView>(R.id.item_main_tv)
        }
    }
}