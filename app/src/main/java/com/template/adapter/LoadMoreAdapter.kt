package com.template.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.template.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry:() -> Unit):LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {



    inner class ViewHolder(val binding: LoadMoreBinding) :RecyclerView.ViewHolder(binding.root){
        fun loadState(){
            binding.progress.visibility = View.VISIBLE
            binding.error.visibility = View.GONE
        }
        fun loadError(retry: () -> Unit){
            binding.progress.visibility= View.GONE
            binding.error.visibility =View.VISIBLE
            binding.error.setOnClickListener { retry() }
        }
        fun notLoading(){
            binding.progress.visibility= View.GONE
            binding.error.visibility = View.GONE


        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = LoadMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Loading){
            holder.loadState()
        }else if (loadState is LoadState.Error){
            holder.loadError(retry)
        }else if (loadState is LoadState.NotLoading){
            holder.notLoading()
        }
    }
}