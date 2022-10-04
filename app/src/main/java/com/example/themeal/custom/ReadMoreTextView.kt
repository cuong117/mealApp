package com.example.themeal.custom

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.themeal.R

class ReadMoreTextView(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {

    private var showText = ""
    private var hideText = ""
    private var fullText = ""
    private var dots = "..."
    private var showLine = 0
    private var isHide = true

    init {
        val typeArr = context.obtainStyledAttributes(attr, R.styleable.ReadMoreTextView)
        try {
            showLine = typeArr.getInteger(R.styleable.ReadMoreTextView_show_line, 0)
            hideText = typeArr.getString(R.styleable.ReadMoreTextView_hide_text) ?: ""
            showText = typeArr.getString(R.styleable.ReadMoreTextView_show_text) ?: ""
        } finally {
            typeArr.recycle()
        }
        viewTreeObserver.addOnGlobalLayoutListener {
            if (isHide && showLine < lineCount) {
                createShowMore()
            } else if (isHide.not()) {
                createHideButton()
            }
        }
    }

    private fun createShowMore() {
        fullText = text.toString()
        var showText = ""
        val endIndex = layout.getLineEnd(showLine - 1)
        showText += text.substring(0, endIndex)
        showText = showText.substring(
            0,
            endIndex - this.showText.length - dots.length - 1
        ) + dots + this.showText
        text = showText
        setExtendText(this.showText.length + dots.length) {
            isHide = false
            text = fullText
        }
    }

    private fun createHideButton() {
        text = text.toString() + dots + hideText
        setExtendText(dots.length + hideText.length) {
            isHide = true
            text = fullText
            createShowMore()
        }
    }

    private fun setExtendText(length: Int, change: () -> Unit) {
        val spanString = SpannableString(text)
        spanString.setSpan(
            object : ClickableSpan() {

                override fun updateDrawState(ds: TextPaint) {
                    paint.isUnderlineText = false
                }

                override fun onClick(widget: View) {
                    change()
                }
            },
            text.length - length,
            text.length,
            0
        )

        spanString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            text.length - length,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        movementMethod = LinkMovementMethod.getInstance()
        text = spanString
    }
}
