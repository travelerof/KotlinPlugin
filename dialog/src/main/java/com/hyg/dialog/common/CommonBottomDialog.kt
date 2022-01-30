package com.hyg.dialog.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.hyg.dialog.OnDialogClickListener
import com.hyg.dialog.R
import com.hyg.dialog.TextListener

/**
 * @Author 韩永刚
 * @Date 2022/01/30
 * @Desc
 */
class CommonBottomDialog private constructor(context: Context,private val options: DialogOptions):BottomDialog(context) {

    private val vh =
        VHolder(LayoutInflater.from(context).inflate(R.layout.dialog_common_bottom_layout, null))

    init {
        initView()
        initData()
    }

    private fun initView() {
        setContentView(vh.getView())
        setCancelable(options.cancelable)
        setCanceledOnTouchOutside(options.canOutside)
    }

    private fun initData() {
        initTitle()
        initMessage()
        initBottomLine()
        initBottom()
    }

    /**
     * 初始化标题
     */
    private fun initTitle() {
        vh.tvTitle.visibility = if (options.title == "") {
            View.GONE
        } else {
            vh.tvTitle.text = options.title
            options.titleListener?.onText(vh.tvTitle)
            View.VISIBLE
        }
        vh.titleLine.visibility = if (showTitleLine()) View.VISIBLE else View.GONE
    }


    private fun initMessage() {
        vh.messageLayout.visibility = if (!options.hasMessage()) {
            View.GONE
        } else {
            vh.tvMessage.visibility = if (options.message == "") {
                vh.messageLayout.addView(options.contentView)
                View.GONE
            } else {
                vh.tvMessage.text = options.message
                options.messageListener?.onText(vh.tvMessage)
                View.VISIBLE
            }
            View.VISIBLE
        }
    }

    private fun initBottomLine() {
        vh.messageLineView.visibility = if (showBottomLine()) View.VISIBLE else View.GONE
    }

    private fun initBottom() {
        vh.bottomLayout.visibility = if (options.hasBottomButton()) {
            if (options.negativeText == "" && options.positiveText != "") {
                vh.tvPositive.visibility = View.VISIBLE
                vh.tvNegative.visibility = View.GONE
                vh.negativeLineView.visibility = View.GONE
                vh.tvPositive.text = options.positiveText
                options.positiveTextListener?.onText(vh.tvPositive)
                vh.tvPositive.setOnClickListener {
                    options.positiveClickListener?.onClick(this)
                }
            } else if (options.negativeText != "" && options.positiveText == "") {
                vh.tvPositive.visibility = View.GONE
                vh.tvNegative.visibility = View.VISIBLE
                vh.negativeLineView.visibility = View.GONE
                vh.tvNegative.text = options.negativeText
                options.negativeTextListener?.onText(vh.tvNegative)
                vh.tvNegative.setOnClickListener {
                    options.negativeClickListener?.onClick(this)
                }
            } else {
                vh.tvPositive.visibility = View.VISIBLE
                vh.tvNegative.visibility = View.VISIBLE
                vh.negativeLineView.visibility = if (options.showLine) View.VISIBLE else View.GONE
                vh.tvNegative.text = options.negativeText
                vh.tvPositive.text = options.positiveText
                options.negativeTextListener?.onText(vh.tvNegative)
                options.positiveTextListener?.onText(vh.tvPositive)
                vh.tvNegative.setOnClickListener {
                    options.negativeClickListener?.onClick(this)
                }
                vh.tvPositive.setOnClickListener {
                    options.positiveClickListener?.onClick(this)
                }
            }
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun width(): Int =
        if (options.width <= 0) {
            WindowManager.LayoutParams.MATCH_PARENT
        } else options.width

    override fun height(): Int =
        if (options.height <= 0) {
            WindowManager.LayoutParams.WRAP_CONTENT
        } else options.height

    override fun alpha(): Float = options.alpha

    /**
     * 是否展示标题分割线
     *
     * @return Boolean
     */
    private fun showTitleLine(): Boolean =
        options.title != "" && options.showLine && options.hasMessage()

    /**
     * 是否展示底部分割线
     *
     * @return Boolean
     */
    private fun showBottomLine(): Boolean =
        options.hasMessage() && (options.negativeText != "" || options.positiveText != "") && options.showLine

    class VHolder(private val view: View) {
        val tvTitle = view.findViewById<TextView>(R.id.dialog_common_bottom_title_tv)
        val titleLine = view.findViewById<View>(R.id.dialog_common_bottom_title_line)
        val messageLayout = view.findViewById<FrameLayout>(R.id.dialog_common_bottom_message_layout)
        val tvMessage = view.findViewById<TextView>(R.id.dialog_common_bottom_message_tv)
        val messageLineView = view.findViewById<View>(R.id.dialog_common_bottom_message_line)
        val bottomLayout = view.findViewById<LinearLayout>(R.id.dialog_common_bottom_b_layout)
        val tvNegative = view.findViewById<TextView>(R.id.dialog_common_bottom_b_negative_tv)
        val negativeLineView = view.findViewById<View>(R.id.dialog_common_bottom_b_negative_line)
        val tvPositive = view.findViewById<TextView>(R.id.dialog_common_bottom_b_positive_tv)

        fun getView(): View = view
    }

    class Builder(private val context: Context) {

        private val options = DialogOptions()

        fun width(width: Int): Builder {
            options.width = width
            return this
        }

        fun height(height: Int): Builder {
            options.height = height
            return this
        }

        fun alpha(alpha: Float): Builder {
            options.alpha = alpha
            return this
        }

        fun canTouchOutside(outSide: Boolean): Builder {
            options.canOutside = outSide
            return this
        }

        fun title(title: String): Builder {
            return title(title,null)
        }

        fun title(title: String,textListener: TextListener?): Builder {
            options.title = title
            options.titleListener = textListener
            return this
        }

        fun message(message: String): Builder {
            return message(message,null)
        }

        fun message(message: String,textListener: TextListener?): Builder {
            options.message = message
            options.messageListener = textListener
            return this
        }

        fun contentView(view: View): Builder {
            options.contentView = view
            return this
        }

        fun showLine(show: Boolean): Builder {
            options.showLine = show
            return this
        }

        fun negative(negative: String, clickListener: OnDialogClickListener): Builder {
            return negative(negative, null, clickListener)
        }

        fun negative(
            negative: String,
            textListener: TextListener?,
            clickListener: OnDialogClickListener
        ): Builder {
            options.negativeText = negative
            options.negativeTextListener = textListener
            options.negativeClickListener = clickListener
            return this
        }

        fun positive(positive: String, clickListener: OnDialogClickListener): Builder {
            return positive(positive, null, clickListener)
        }

        fun positive(
            positive: String,
            textListener: TextListener?,
            clickListener: OnDialogClickListener
        ): Builder {
            options.positiveText = positive
            options.positiveTextListener = textListener
            options.positiveClickListener = clickListener
            return this
        }

        fun create(): CommonBottomDialog = CommonBottomDialog(context, options)
    }
}