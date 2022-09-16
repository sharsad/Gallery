package com.gallery.activity.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gallery.R
import com.gallery.model.imageFolder
import com.gallery.util.itemClickListener
import kotlinx.android.synthetic.main.picture_folder_item.view.*

class pictureFolderAdapter(
    private var folders: ArrayList<imageFolder>,
    private var folderContx: Context,
    private var listen: itemClickListener
) : RecyclerView.Adapter<pictureFolderAdapter.FolderHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cell: View = inflater.inflate(R.layout.picture_folder_item, parent, false)
        return FolderHolder(cell)
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        holder.setData(folders[position], position)
    }

    inner class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(folder: imageFolder, position: Int) {
            Glide.with(folderContx)
                .load(folder.firstPic)
                .apply(RequestOptions().centerCrop())
                .into(itemView.folderPic)

            //setting the number of images
            val text = "" + folder.folderName
            val folderSizeString = "" + folder.numberOfPics.toString() + " Media"
            itemView.folderSize.text = folderSizeString
            itemView.folderName.text = text
            if(folder.folderName=="All Videos"){
                itemView.video_play.visibility=View.VISIBLE
            }else{
                itemView.video_play.visibility=View.INVISIBLE
            }
            itemView.folderPic.setOnClickListener {
                listen.onPicClicked(
                    folder.path,
                    folder.folderName
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return folders.size
    }

}