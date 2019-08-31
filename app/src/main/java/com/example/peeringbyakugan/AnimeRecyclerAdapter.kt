package com.example.peeringbyakugan

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.peeringbyakugan.databinding.ItemLayoutBinding

class AnimeRecyclerAdapter : RecyclerView.Adapter<AnimeRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeRecyclerAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeTextView: TextView = itemView.findViewById(R.id.animeTitle)
        val animeImageView: ImageView = itemView.findViewById(R.id.animeImageView)



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemLayoutBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_layout,parent,false)
                return ViewHolder(binding.root)
            }
        }

        fun bind() {
            animeImageView.setImageResource(R.drawable.test)
            animeTextView.text = "Naruto"
        }
    }

}


class AnimeInstanceDiffCallback : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}