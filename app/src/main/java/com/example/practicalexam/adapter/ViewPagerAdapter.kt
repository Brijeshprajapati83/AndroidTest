package com.example.practicalexam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.practicalexam.R


internal class ViewPagerAdapter(var context: Context, private var list: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any
    {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_viewpager, container, false)
        val imageview = view.findViewById<ImageView>(R.id.imageview)

        Glide.with(context)
            .load(list[position])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageview)
        container.addView(view)
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
    {
        container.removeView(`object` as View)
    }
}