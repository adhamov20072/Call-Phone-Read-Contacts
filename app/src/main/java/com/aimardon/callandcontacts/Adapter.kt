package com.aimardon.callandcontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aimardon.callandcontacts.databinding.RecyclerItemLayoutBinding

class Adapter: ListAdapter<PNdata, Adapter.MyViewHolder>(diffUtil) {
    var listener:Click ?=null
    class MyViewHolder(val binding: RecyclerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PNdata>() {
            override fun areItemsTheSame(oldItem: PNdata, newItem: PNdata): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PNdata, newItem: PNdata): Boolean {
                return oldItem.phone == newItem.phone
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.Name.text = item.name
        holder.binding.NUmber.text = item.phone
        holder.itemView.setOnClickListener {

        }
    }
    interface Click{
        fun clicklistener()
    }
    fun onClickListenerr(click: Click){
        listener=click
    }
}