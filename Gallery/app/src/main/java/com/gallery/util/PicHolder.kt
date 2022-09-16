package com.gallery.util

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gallery.R

class PicHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var picture: ImageView
    var video_play: ImageView

    init {
        picture = itemView.findViewById(R.id.image)
        video_play = itemView.findViewById(R.id.video_play)
    }
}