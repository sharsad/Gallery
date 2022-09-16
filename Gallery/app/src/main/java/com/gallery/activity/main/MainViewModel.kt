package com.gallery.activity.main

import android.app.Application
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.gallery.model.imageFolder

class MainViewModel(application: Application) : AndroidViewModel(application){



    fun getPicturePaths(): ArrayList<imageFolder> {
        val picFolders: ArrayList<imageFolder> = ArrayList()
        val picPaths = ArrayList<String>()
        val allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
        )
        val context = getApplication<Application>().applicationContext
        val cursor: Cursor? =
            context.contentResolver.query(allImagesuri, projection, null, null, null)
        try {
            cursor?.moveToFirst()
            do {
                val folds = imageFolder()
                val name =
                    cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val folder =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                //String folderpaths =  datapath.replace(name,"");
                var folderpaths = datapath.substring(0, datapath.lastIndexOf("$folder/"))
                folderpaths = "$folderpaths$folder/"
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths)
                    folds.path =folderpaths
                    folds.folderName = folder
                    folds.firstPic = datapath //if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addPics()
                    picFolders.add(folds)
                } else {
                    for (i in picFolders.indices) {
                        if (picFolders[i].path.equals(folderpaths)) {
                            picFolders[i].firstPic=datapath
                            picFolders[i].addPics()
                        }
                    }
                }
            } while (cursor!!.moveToNext())
            if(picFolders.size>0) {
                val folds = imageFolder()
                folds.path = picFolders[picFolders.size-1].path
                folds.folderName = "All Images"
                folds.firstPic =
                    picFolders[picFolders.size-1].firstPic //if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
               var total = 0
                picFolders.forEach {
                    total += it.numberOfPics
                }
                folds.numberOfPics=total
                picFolders.add(0,folds)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (i in picFolders.indices) {
            Log.d("picture folders",
                picFolders[i].folderName
                    .toString() + " and path = " + picFolders[i].path+ " " + picFolders[i].numberOfPics
            )
        }
        return picFolders
    }
    fun getVideoPath(): imageFolder {
        val folds = imageFolder()
        val allvideouri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.BUCKET_ID
        )
        val context = getApplication<Application>().applicationContext
        val cursor: Cursor? =
            context.contentResolver.query(allvideouri, projection, null, null, null)
        try {
            cursor?.moveToFirst()

            val name =
                cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
            val folder =
                cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
            val datapath =
                cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))

            folds.path = "All Videos"
            folds.folderName = "All Videos"
            folds.firstPic =
                datapath //if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
            do {
                folds.addPics()
            }while (cursor!!.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return folds
    }
}