package com.example.kotlinplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val tv = findViewById<TextView>(R.id.loading_tv)
        tv.viewTreeObserver.addOnGlobalLayoutListener {
            BadgeDrawable.create(this).apply {
                badgeGravity = BadgeDrawable.TOP_END
                number = 20
                backgroundColor = ContextCompat.getColor(this@LoadingActivity,R.color.cardview_dark_background)
                isVisible = true
                BadgeUtils.attachBadgeDrawable(this,tv)
            }
        }
    }
}