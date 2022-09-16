package com.gallery.activity.display.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.core.view.ViewCompat
import android.content.Context
import android.view.View
import com.gallery.R
import com.gallery.util.PicHolder
import com.gallery.util.itemClickListener
import com.gallery.util.pictureFacer
import kotlinx.android.synthetic.main.picture_folder_item.view.*
import java.util.ArrayList

class PictureAdapter(
    private val pictureList: ArrayList<pictureFacer>?,
    private val folderName: String,
    private val pictureContext: Context,
    private val picListerner: itemClickListener
) : RecyclerView.Adapter<PicHolder>() {
    override fun onCreateViewHolder(container: ViewGroup, position: Int): PicHolder {
        val inflater = LayoutInflater.from(container.context)
        val cell = inflater.inflate(R.layout.pic_holder_item, container, false)
        return PicHolder(cell)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        val image = pictureList!![position]
        Glide.with(pictureContext)
            .load(image.picturePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.picture)
        ViewCompat.setTransitionName(holder.picture, position.toString() + "_image")
        if(folderName=="All Videos"){
            holder.video_play.visibility= View.VISIBLE
        }else{
            holder.video_play.visibility= View.INVISIBLE
        }
        holder.picture.setOnClickListener {
            if(folderName =="All Videos"){
                picListerner.onPicClicked(
                    image.picturePath,
                    folderName
                )
            }else{
                picListerner.onPicClicked(
                    holder,
                    position,
                    pictureList
                )
            }


        }
    }

    override fun getItemCount(): Int {
        return pictureList!!.size
    }
}