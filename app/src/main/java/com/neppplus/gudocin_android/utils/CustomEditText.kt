package com.neppplus.gudocin_android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.neppplus.gudocin_android.R

class CustomEditText : AppCompatEditText, TextWatcher, View.OnTouchListener,
    View.OnFocusChangeListener {
    private lateinit var clearDrawable: Drawable
    private lateinit var onfocuschangelistener: OnFocusChangeListener
    private lateinit var onTouchListener: OnTouchListener
    private var count: Int = 0

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        clearDrawable = DrawableCompat.wrap(
            (ResourcesCompat.getDrawable(resources, R.drawable.ic_clear, null) as Drawable)
        )
        DrawableCompat.setTintList(clearDrawable, hintTextColors)
        clearDrawable.setBounds(0, 0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            clearDrawable.colorFilter =
                PorterDuffColorFilter(context.getColor(R.color.teal_200), PorterDuff.Mode.SRC_IN)
        }
        setClearIconVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    private fun setClearIconVisible(visible: Boolean) {
        clearDrawable.setVisible(visible, false)
        setCompoundDrawables(null, null, if (visible) clearDrawable else null, null)
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener) {
        onfocuschangelistener = l
        count = 1
    }

    override fun setOnTouchListener(l: OnTouchListener) {
        onTouchListener = l
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isFocused) setClearIconVisible(text!!.isNotEmpty())
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        try {
            val x = event.x.toInt()
            if (clearDrawable.isVisible && x > width - paddingRight - clearDrawable.intrinsicWidth) {
                if (event.action == MotionEvent.ACTION_UP) {
                    error = null
                    text = null
                    Toast.makeText(context, "메모 삭제", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            return onTouchListener.onTouch(v, event)
        } catch (i: Exception) {
            return false
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus && text != null) setClearIconVisible(text!!.isNotEmpty())
        else setClearIconVisible(false)
        if (count == 1) onfocuschangelistener.onFocusChange(v, hasFocus)
    }
}