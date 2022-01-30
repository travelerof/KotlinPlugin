package com.hyg.log.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.hyg.dialog.BaseDialog
import com.hyg.dialog.common.CommonDialog
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.TextListener
import com.hyg.log.data.DataType
import com.hyg.log.data.OnLogChangedListener
import com.hyg.log.data.manager.DebugDataManager
import com.hyg.log.data.model.LogModel
import com.hyg.widget.label.LabelView
import com.hyg.widget.label.OnLabelClickListener

class DebugListActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ivClear: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var searchLayout: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var ivDelete: ImageView
    private lateinit var ivSearch: ImageView
    private lateinit var labelView: LabelView
    private lateinit var rv: RecyclerView

    private val tabMaps = linkedMapOf(
        "日志" to DataType.LOG,
        "缓存日志" to DataType.LOG_CACHE,
        "崩溃日志" to DataType.CRASH,
    )

    private val data = mutableListOf<LogModel>()
    private lateinit var adapter: DebugListAdapter

    private var currentTabType: Int = DataType.LOG

    private val logChangedListener = object : OnLogChangedListener {
        override fun onLogChanged(type: Int, logs: MutableList<LogModel>) {
            if (currentTabType == type) {
                data.clear()
                data.addAll(logs)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_list)
        initView()
        initListener()
        DebugDataManager.query(currentTabType,"")
    }

    private fun initView() {
        ivBack = findViewById(R.id.debug_title_back_iv)
        tvTitle = findViewById(R.id.debug_title_info_tv)
        ivClear = findViewById(R.id.debug_title_handle_iv)
        tabLayout = findViewById(R.id.debug_list_tab)
        searchLayout = findViewById(R.id.debug_log_search_layout)
        etSearch = findViewById(R.id.debug_log_search_et)
        ivDelete = findViewById(R.id.debug_search_delete_iv)
        ivSearch = findViewById(R.id.debug_search_log_iv)
        labelView = findViewById(R.id.debug_log_history_view)
        rv = findViewById(R.id.debug_list_rv)
        ivClear.setImageResource(R.mipmap.ic_debug_clear)
        adapter = DebugListAdapter(this, data)
        rv.adapter = adapter
        tabMaps.keys.forEach {
            val tab = tabLayout.newTab()
            tab.text = it
            tab.contentDescription = it
            tabLayout.addTab(tab)
        }
    }

    private fun initListener() {
        DebugDataManager.addListener(logChangedListener)
        ivBack.setOnClickListener {
            finish()
        }
        ivClear.setOnClickListener { clear() }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    checkedTab(tabMaps[it.text] ?: DataType.LOG)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        labelView.setOnLabelClickListener(object : OnLabelClickListener{
            override fun onLabelClick(text: String) {
                etSearch.setText(text)
            }
        })
        ivDelete.setOnClickListener {
            etSearch.setText("")
        }
        ivSearch.setOnClickListener {
            val keyword = etSearch.text.toString()
            if (keyword != "") {
                labelView.addLabel(keyword)
            }
            DebugDataManager.query(currentTabType, keyword)
        }

        adapter.setOnLogItemClickListener(object : DebugListAdapter.OnLogItemClickListener{
            override fun onItemClick(position: Int, logModel: LogModel) {
                val intent = when (currentTabType) {
                    DataType.LOG_CACHE,DataType.CRASH -> {
                        val intent = Intent(this@DebugListActivity,DebugFileInfoActivity::class.java)
                        intent.putExtra("fileName",logModel.key)
                        intent.putExtra("fileType",currentTabType)
                    }
                    else -> Intent(this@DebugListActivity,DebugLogInfoActivity::class.java)
                }
                startActivity(intent)
            }
        })

    }


    private fun checkedTab(type: Int) {
        if (currentTabType == type) {
            return
        }
        currentTabType = type
        etSearch.setText("")
        searchLayout.visibility = if (type == DataType.LOG) View.VISIBLE else View.GONE
        data.clear()
        adapter.setType(currentTabType)
        adapter.notifyDataSetChanged()
        DebugDataManager.query(type, "")
    }

    private fun clear(){
        val message = when (currentTabType) {
            DataType.LOG_CACHE -> "是否清空缓存日志文件夹?"
            DataType.CRASH -> "是否清空崩溃日志文件夹?"
            else -> "是否清空在线日志?"
        }
        CommonDialog.Builder(this)
            .message(message,object : TextListener {
                override fun onText(text: TextView) {
                    text.gravity = Gravity.CENTER
                }
            })
            .negative("取消",object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                }
            })
            .positive("清空",object : OnDialogClickListener {
                override fun onClick(dialog: BaseDialog) {
                    dialog.dismiss()
                    DebugDataManager.clear(currentTabType)
                }
            })
            .create()
            .show()
    }


    override fun onDestroy() {
        DebugDataManager.removeListener(logChangedListener)
        super.onDestroy()
    }
}