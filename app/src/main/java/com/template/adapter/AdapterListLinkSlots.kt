package com.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.template.R
import com.template.databinding.ItemListLinkSlotBinding
import com.template.data.model.ui.Slots

class AdapterListLinkSlots(val context: Context):RecyclerView.Adapter<AdapterListLinkSlots.ListLinlSlotsViewHolder>() {
    private var slotsList:List<Slots> = listOf()

    fun setSlotsList(slots: List<Slots>){
       slotsList = slots
        notifyDataSetChanged()
    }
    class ListLinlSlotsViewHolder (var binding: ItemListLinkSlotBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(slots: Slots){
            binding.apply {
                name.text = slots.name

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLinlSlotsViewHolder {
        val view = ListLinlSlotsViewHolder(ItemListLinkSlotBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return view
    }

    override fun getItemCount(): Int = slotsList.size

    override fun onBindViewHolder(holder: ListLinlSlotsViewHolder, position: Int) {
        val slots = slotsList[position]
        holder.bind(slots)
        holder.binding.name.text = slots.name

        Glide.with(context)
            .load(slots.image)
            .placeholder(R.drawable.not_image)
            .into(holder.binding.image)
    }
}