package com.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.template.R
import com.template.data.model.ui.Games


class AdapterListLinkGames(val context: Context):PagingDataAdapter<Games, AdapterListLinkGames.GamesViewHolder>(DiffCallback()) {
    private var loadState:LoadState? = null
    companion object {
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_NORMAL = 1
    }
    fun setLoadState(loadState: LoadState) {
        this.loadState = loadState
        notifyDataSetChanged()
      //  if (loadState is LoadState.Loading){
        //    notifyItemInserted(itemCount - 1)
       // }

    }
    class GamesViewHolder(view:View):ViewHolder(view){
        val recoil = view.findViewById<TextView>(R.id.recoil)
        val  nameGames = view.findViewById<TextView>(R.id.nameGames)
        val  bonus = view.findViewById<TextView>(R.id.bonus)
        val cashback = view.findViewById<TextView>(R.id.cashback)
        val image = view.findViewById<ImageView>(R.id.image)

        fun bind(games: Games?){
           recoil.text = "45-46%"
            nameGames.text = games!!.name
            bonus.text = games.bonus
            cashback.text = "${games.cashback}%"

        }
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val games = getItem(position)
        holder.bind(games)
        holder.recoil.text = "45-46%"
        holder.apply {
            nameGames.text = games!!.name
            bonus.text = games.bonus
            cashback.text = "${games.cashback}%"
        }

        Glide.with(context)
            .load(games!!.image)
            .placeholder(R.drawable.not_image)
            .into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return when(viewType){
            VIEW_TYPE_LOADING -> {
                GamesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.load_more,parent,false))
            }
            VIEW_TYPE_NORMAL->{
                GamesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_link_games,parent,false))
            }
            else->{
                GamesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_link_games,parent,false))
            }
        }

    }

   override fun getItemViewType(position: Int): Int {
      return when{
          loadState is LoadState.Loading && position == itemCount -> VIEW_TYPE_LOADING
          loadState is LoadState.Error && position == itemCount -> VIEW_TYPE_LOADING
          else -> VIEW_TYPE_NORMAL
      }
    }
}
class DiffCallback:DiffUtil.ItemCallback<Games>(){
    override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean {
       return oldItem.name == newItem.name    }

    override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean {
        return oldItem == newItem }

}


