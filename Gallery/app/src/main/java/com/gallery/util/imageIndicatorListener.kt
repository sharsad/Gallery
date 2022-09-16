package com.gallery.util

interface imageIndicatorListener {
    /**
     *
     * @param ImagePosition position of an item in the RecyclerView Adapter
     */
    fun onImageIndicatorClicked(ImagePosition: Int)
}