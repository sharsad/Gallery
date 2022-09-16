package com.gallery.fragment


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import android.annotation.SuppressLint
import android.content.Context

import androidx.viewpager.widget.ViewPager
import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.View.OnTouchListener
import androidx.viewpager.widget.PagerAdapter
import android.os.Handler
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.gallery.R
import com.gallery.util.imageIndicatorListener
import com.gallery.util.pictureFacer
import java.util.ArrayList


class pictureBrowserFragment : Fragment, imageIndicatorListener {
    private var allImages: ArrayList<pictureFacer>? = ArrayList()
    private var position = 0
    private var animeContx: Context? = null
    private lateinit var image: ImageView
    private lateinit var imagePager: ViewPager
    private lateinit var indicatorRecycler: RecyclerView
    private var viewVisibilityController = 0
    private var viewVisibilitylooper = 0
    private var pagingImages: ImagesPagerAdapter? = null
    private var previousSelected = -1

    constructor()
    constructor(allImages: ArrayList<pictureFacer>?, imagePosition: Int, anim: Context?) {
        this.allImages = allImages
        position = imagePosition
        animeContx = anim
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_browser, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewVisibilityController = 0
        viewVisibilitylooper = 0
        /**
         * setting up the viewPager with images
         */
        imagePager = view.findViewById(R.id.imagePager)
        pagingImages = ImagesPagerAdapter()
        imagePager.adapter = pagingImages
        imagePager.offscreenPageLimit = 3
        imagePager.currentItem = position //displaying the image at the current position passed by the ImageDisplay Activity
        /**
         * setting up the recycler view indicator for the viewPager
         */
        indicatorRecycler = view.findViewById(R.id.indicatorRecycler)
        indicatorRecycler.hasFixedSize()
        indicatorRecycler.layoutManager = GridLayoutManager(
            context,
            1,
            RecyclerView.HORIZONTAL,
            false
        )
        val indicatorAdapter: RecyclerView.Adapter<*> =
            recyclerViewPagerImageIndicator(allImages, context, this)
        indicatorRecycler.adapter = indicatorAdapter

        //adjusting the recyclerView indicator to the current position of the viewPager, also highlights the image in recyclerView with respect to the
        //viewPager's position
        allImages!![position].selected = true
        previousSelected = position
        indicatorAdapter.notifyDataSetChanged()
        indicatorRecycler.scrollToPosition(position)
        /**
         * this listener controls the visibility of the recyclerView
         * indication and it current position in respect to the image ViewPager
         */
        imagePager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (previousSelected != -1) {
                    allImages!![previousSelected].selected = false
                    previousSelected = position
                    allImages!![position].selected = true
                    indicatorRecycler.adapter!!.notifyDataSetChanged()
                    indicatorRecycler.scrollToPosition(position)
                } else {
                    previousSelected = position
                    allImages!![position].selected = true
                    indicatorRecycler.adapter!!.notifyDataSetChanged()
                    indicatorRecycler.scrollToPosition(position)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        indicatorRecycler.setOnTouchListener { v, event ->
            /*if(viewVisibilityController == 0){
                        indicatorRecycler.setVisibility(View.VISIBLE);
                        visibiling();
                    }else{
                        viewVisibilitylooper++;
                    }*/
            false
        }
    }


    override fun onImageIndicatorClicked(ImagePosition: Int) {

        //the below lines of code highlights the currently select image in  the indicatorRecycler with respect to the viewPager position
        if (previousSelected != -1) {
            allImages!![previousSelected].selected = false
            previousSelected = ImagePosition
            indicatorRecycler.adapter!!.notifyDataSetChanged()
        } else {
            previousSelected = ImagePosition
        }
        imagePager.currentItem = ImagePosition
    }

    /**
     * the imageViewPager's adapter
     */
    inner class ImagesPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return allImages!!.size
        }

        override fun instantiateItem(containerCollection: ViewGroup, position: Int): Any {
            val layoutinflater =
                containerCollection.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutinflater.inflate(R.layout.picture_browser_pager, null)
            image = view.findViewById(R.id.image)
            ViewCompat.setTransitionName(image, position.toString() + "picture")
            val pic = allImages!![position]
            Glide.with(animeContx!!)
                .load(pic.picturePath)
                .apply(RequestOptions().fitCenter())
                .into(image)
            image.setOnClickListener(View.OnClickListener {
                if (indicatorRecycler.visibility == View.GONE) {
                    indicatorRecycler.visibility = View.VISIBLE
                } else {
                    indicatorRecycler.visibility = View.GONE
                }
                /**
                 * uncomment the below condition and comment the one above to control recyclerView visibility automatically
                 * when image is clicked
                 */
                /**
                 * uncomment the below condition and comment the one above to control recyclerView visibility automatically
                 * when image is clicked
                 */
                /*if(viewVisibilityController == 0){
                     indicatorRecycler.setVisibility(View.VISIBLE);
                     visibiling();
                 }else{
                     viewVisibilitylooper++;
                 }*/
            })
            (containerCollection as ViewPager).addView(view)
            return view
        }

        override fun destroyItem(containerCollection: ViewGroup, position: Int, view: Any) {
            (containerCollection as ViewPager).removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

    /**
     * function for controlling the visibility of the recyclerView indicator
     */
    private fun visibiling() {
        viewVisibilityController = 1
        val checker = viewVisibilitylooper
        Handler().postDelayed({
            if (viewVisibilitylooper > checker) {
                visibiling()
            } else {
                indicatorRecycler.visibility = View.GONE
                viewVisibilityController = 0
                viewVisibilitylooper = 0
            }
        }, 4000)
    }

    companion object {
        fun newInstance(
            allImages: ArrayList<pictureFacer>?,
            imagePosition: Int,
            anim: Context?
        ): pictureBrowserFragment {
            return pictureBrowserFragment(allImages, imagePosition, anim)
        }
    }
}