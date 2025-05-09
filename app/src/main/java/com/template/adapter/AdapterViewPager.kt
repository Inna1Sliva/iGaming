package com.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.template.R
import com.template.databinding.LayoutImageViewPagerBinding
import com.template.data.model.ui.Link

class AdapterViewPager(val context:Context): RecyclerView.Adapter<AdapterViewPager.ViewPagerViewHolder>() {

    private var imageList:List<Link> = listOf()

    fun setImageList(image: List<Link>){
        imageList = image
        notifyDataSetChanged()
    }
    inner class ViewPagerViewHolder(var binding:LayoutImageViewPagerBinding ): RecyclerView.ViewHolder(binding.root) {

       // fun bind (list: Image) {

          //  list.image = binding.image


       // }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(LayoutImageViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
       val image = imageList[position]
       // holder.bind(image)
        Glide.with(context)
            .load(image.link)
            .placeholder(R.drawable.not_image)
            .into(holder.binding.image)
    }

}