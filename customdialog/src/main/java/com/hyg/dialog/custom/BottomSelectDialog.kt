package com.hyg.dialog.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyg.dialog.BaseDialog
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.TextListener
import com.hyg.dialog.common.BottomDialog

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
class BottomSelectDialog private constructor(context: Context, private val options: BottomSelectorOptions) :
    BottomDialog(context) {
    private val view = LayoutInflater.from(context).inflate(R.layout.dialog_select_bottom,null)
    private val rv = view.findViewById<RecyclerView>(R.id.dialog_bottom_rv)
    private val line = view.findViewById<View>(R.id.dialog_bottom_cancel_line)
    private val tvCancel = view.findViewById<TextView>(R.id.dialog_bottom_cancel_tv)
    private val adapter = BottomSelectAdapter(context,this,options)
    init {
        setContentView(view)
        rv.adapter = adapter
        if (options.hasCancel) {
            line.visibility = View.VISIBLE
            tvCancel.visibility = View.VISIBLE
            options.cancelTextListener?.onText(tvCancel)
            tvCancel.setOnClickListener {
                options.cancelListener?.onClick(this) ?:dismiss()
            }
        }else{
            line.visibility = View.GONE
            tvCancel.visibility = View.GONE
        }
    }

    override fun width(): Int = options.width

    class Builder(private val context: Context) {
        private val options = BottomSelectorOptions()

        fun width(width: Int): Builder {
            options.width = width
            return this
        }

        fun hasCancel(hasCancel: Boolean): Builder {
            options.hasCancel = hasCancel
            return this
        }

        fun cancelListener(textListener: TextListener?): Builder {
            return cancelListener(textListener, null)
        }

        fun cancelListener(
            textListener: TextListener?,
            onDialogCancelListener: OnDialogClickListener?
        ): Builder {
            options.cancelTextListener = textListener
            options.cancelListener = onDialogCancelListener
            return this
        }

        fun itemData(itemData: MutableList<String>): Builder {
            options.itemData += itemData
            return this
        }

        fun itemListener(onItemClickListener: OnDialogItemClickListener): Builder {
            options.onItemClickListener = onItemClickListener
            return this
        }

        fun create(): BottomSelectDialog = BottomSelectDialog(context, options)
    }

    class BottomSelectAdapter(
        private val context: Context,
        private val dialog: BaseDialog,
        private val options: BottomSelectorOptions
    ) : RecyclerView.Adapter<VHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder = VHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_bottom_select_item_layout, parent, false)
        )

        override fun onBindViewHolder(holder: VHolder, position: Int) {
            holder.line.visibility = if (position == options.itemData.size - 1) {
                View.GONE
            } else {
                View.VISIBLE
            }
            holder.tv.setBackgroundResource(if (position == 0) R.drawable.select_bottom_item_first else R.drawable.select_bottom_item_default)
            holder.tv.text = options.itemData[position]
            holder.tv.setOnClickListener {
                options.onItemClickListener?.onItemClick(dialog, holder.tv.text.toString())
            }
        }

        override fun getItemCount(): Int = options.itemData.size

    }

    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.adapter_select_item_tv)
        val line = itemView.findViewById<View>(R.id.adapter_select_item_line)
    }

    class BottomSelectorOptions {
        var width = WindowManager.LayoutParams.MATCH_PARENT
        val itemData = mutableListOf<String>()
        var onItemClickListener: OnDialogItemClickListener? = null
        var hasCancel = true
        var cancelTextListener: TextListener? = null
        var cancelListener: OnDialogClickListener? = null
    }
}