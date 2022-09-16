package com.gallery.model

class imageFolder {
    var path: String? = null
    var folderName: String? = null
    var numberOfPics = 0
    var firstPic: String? = null

    fun addPics() {
        numberOfPics++
    }
}