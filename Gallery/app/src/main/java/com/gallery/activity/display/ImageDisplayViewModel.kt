package com.gallery.activity.display

import android.app.Application
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import com.gallery.util.pictureFacer
import java.lang.Exception
import java.util.ArrayList

class ImageDisplayViewModel(application: Application) : AndroidViewModel(application) {



    fun getAllImagesByFolder(folderName:String,path: String?): ArrayList<pictureFacer> {
        var images = ArrayList<pictureFacer>()
        var allVideosUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )
        val context = getApplication<Application>().applicationContext
        val cursor:Cursor
        when (folderName) {
            "All Images" -> {
                cursor= context.contentResolver.query(allVideosUri, projection, null, null, null)!!
            }
            "All Videos" -> {
                allVideosUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                projection = arrayOf(
                    MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE
                )
                cursor= context.contentResolver.query(allVideosUri, projection, null, null, null)!!
            }
            else -> {
                cursor= context.contentResolver.query(
                    allVideosUri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf(
                        "%$path%"
                    ), null
                )!!
            }
        }

        try {
            cursor.moveToFirst()
            do {
                val pic = pictureFacer()
                if(folderName == "All Videos") {
                    pic.picturName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    pic.picturePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    pic.pictureSize =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                }else{
                    pic.picturName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                    pic.picturePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    pic.pictureSize =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                }
                images.add(pic)
            } while (cursor.moveToNext())
            cursor.close()
            val reSelection = ArrayList<pictureFacer>()
            for (i in images.size - 1 downTo -1 + 1) {
                reSelection.add(images[i])
            }
            images = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }
}