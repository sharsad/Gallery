package com.gallery.fragment


import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.gallery.R
import com.gallery.model.indicatorHolder
import com.gallery.util.imageIndicatorListener
import com.gallery.util.pictureFacer
import java.util.ArrayList

class recyclerViewPagerImageIndicator
/**
 *
 * @param pictureList ArrayList of pictureFacer objects
 * @param pictureContx The Activity of fragment context
 * @param imageListerner Interface for communication between adapter and fragment
 */(
    var pictureList: ArrayList<pictureFacer>?,
    var pictureContx: Context?,
    private val imageListerner: imageIndicatorListener
) : RecyclerView.Adapter<indicatorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): indicatorHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.indicator_holder, parent, false)
        return indicatorHolder(cell)
    }

    override fun onBindViewHolder(holder: indicatorHolder, position: Int) {
        val pic = pictureList!![position]
        holder.positionController.setBackgroundColor(
            if (pic.selected) Color.parseColor("#00000000") else Color.parseColor(
                "#8c000000"
            )
        )
        Glide.with(pictureContx!!)
            .load(pic.picturePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
        holder.image.setOnClickListener { //holder.card.setCardElevation(5);
            pic.selected = true
            notifyDataSetChanged()
            imageListerner.onImageIndicatorClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return pictureList!!.size
    }
}