package com.gallery.model

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gallery.R

class indicatorHolder internal constructor(itemView: View) : ViewHolder(itemView) {
    var image: ImageView
    private val card: CardView
    var positionController: View

    init {
        image = itemView.findViewById(R.id.imageIndicator)
        card = itemView.findViewById(R.id.indicatorCard)
        positionController = itemView.findViewById(R.id.activeImage)
    }
}