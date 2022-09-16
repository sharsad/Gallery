package com.gallery.activity.display


import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gallery.R
import com.gallery.activity.display.adapter.PictureAdapter
import com.gallery.fragment.VideoPlayerFragment
import com.gallery.fragment.pictureBrowserFragment
import com.gallery.util.MarginDecoration
import com.gallery.util.PicHolder
import com.gallery.util.itemClickListener
import com.gallery.util.pictureFacer
import kotlinx.android.synthetic.main.activity_image_display.*


class ImageDisplay : AppCompatActivity(), itemClickListener {

    private lateinit var allPictures: ArrayList<pictureFacer>
    private lateinit var folderPath: String
    private lateinit var folderName: String
    private lateinit var viewModel: ImageDisplayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        viewModel = ViewModelProvider(this)[ImageDisplayViewModel::class.java]

        folderName= intent.getStringExtra("folderName").toString()
        folderNameImage.text = folderName
        folderPath = intent.getStringExtra("folderPath").toString()
        allPictures = ArrayList()
        imageRecycler.addItemDecoration(MarginDecoration(this))
        imageRecycler.hasFixedSize()

        if (allPictures.isEmpty()) {
            loader.visibility = View.VISIBLE
            allPictures = viewModel.getAllImagesByFolder(folderName,folderPath)
            imageRecycler.adapter = PictureAdapter(allPictures,folderName, this@ImageDisplay,this)
            loader.visibility = View.GONE
        }
        imageBack.setOnClickListener {
            finish()
        }
    }

    override fun onPicClicked(holder: PicHolder?, position: Int, pics: ArrayList<pictureFacer>?) {

        val browser: pictureBrowserFragment =
            pictureBrowserFragment.newInstance(pics, position, this@ImageDisplay)

        browser.enterTransition = Fade()
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(holder!!.picture, position.toString() + "picture")
            .add(R.id.displayContainer, browser)
            .addToBackStack(null)
            .commit()
    }

    override fun onPicClicked(pictureFolderPath: String?, folderName: String?) {
        if(folderName == "All Videos") {
            val playerFragment = VideoPlayerFragment.newInstance(pictureFolderPath!!, folderName)

            val transition =
                TransitionInflater.from(this).inflateTransition(android.R.transition.explode)

            playerFragment.enterTransition = transition

            supportFragmentManager.beginTransaction()
                .replace(R.id.play_holder, playerFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}