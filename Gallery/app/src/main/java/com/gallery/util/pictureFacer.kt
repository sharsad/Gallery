package com.gallery.util


class pictureFacer {
    var picturName: String? = null
    var picturePath: String? = null
    var pictureSize: String? = null
    var imageUri: String? = null
    var selected = false
    constructor()
    constructor(
        picturName: String?,
        picturePath: String?,
        pictureSize: String?,
        imageUri: String?
    ) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.pictureSize = pictureSize
        this.imageUri = imageUri
    }
}