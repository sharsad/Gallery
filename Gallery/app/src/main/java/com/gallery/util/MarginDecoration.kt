package com.gallery.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.gallery.R

class MarginDecoration(context: Context) : ItemDecoration() {
    private val margin: Int
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect[margin, margin, margin] = margin
    }

    init {
        margin = context.resources.getDimensionPixelSize(R.dimen.item_margin)
    }
}