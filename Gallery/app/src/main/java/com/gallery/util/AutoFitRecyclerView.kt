package com.gallery.util

import android.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet

/**
 * Author CodeBoy722
 * A custom RecyclerView with GridLayout AutoFit Behavior
 */
class AutoFitRecyclerView : RecyclerView {
    private var columnWidth = -1
    private var manager: GridLayoutManager? = null

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initialize(context, attrs)
    }

    /**
     *
     * @param context The Activities Context
     * @param attrs Dimention columnWidth of the RecyclerView
     */
    private fun initialize(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attrsArray = intArrayOf(
                R.attr.columnWidth
            )
            @SuppressLint("ResourceType") val array =
                context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
        manager = GridLayoutManager(getContext(), 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (columnWidth > 0) {
            val spanCount = Math.max(1, measuredWidth / columnWidth)
            manager!!.spanCount = spanCount
        }
    }
}