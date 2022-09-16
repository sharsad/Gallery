package com.gallery.activity.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gallery.R
import com.gallery.activity.display.ImageDisplay
import com.gallery.activity.main.adapter.pictureFolderAdapter
import com.gallery.model.imageFolder
import com.gallery.util.MarginDecoration
import com.gallery.util.PicHolder
import com.gallery.util.itemClickListener
import com.gallery.util.pictureFacer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), itemClickListener {
    private lateinit var viewModel: MainViewModel
    private val requestcode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            requestcode
        )
        init()
    }
    private fun init(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        folderRecycler.addItemDecoration(MarginDecoration(this))
        folderRecycler.hasFixedSize()

        val folds: ArrayList<imageFolder> = viewModel.getPicturePaths()
        val foldsVideo: imageFolder = viewModel.getVideoPath()
        folds.add(1,foldsVideo)

        if (folds.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
            val folderAdapter: RecyclerView.Adapter<*> =
                pictureFolderAdapter(folds, this@MainActivity, this)
            folderRecycler.adapter = folderAdapter
        }
        changeStatusBarColor()
    }
    override fun onPicClicked(
        holder: PicHolder?,
        position: Int,
        pics: java.util.ArrayList<pictureFacer>?
    ) {
        TODO("Not yet implemented")
    }

    override fun onPicClicked(pictureFolderPath: String?, folderName: String?) {
        val move = Intent(this@MainActivity, ImageDisplay::class.java)
        move.putExtra("folderPath", pictureFolderPath)
        move.putExtra("folderName", folderName)
        startActivity(move)
    }
    private fun changeStatusBarColor() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.black)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init()
        }
    }

}