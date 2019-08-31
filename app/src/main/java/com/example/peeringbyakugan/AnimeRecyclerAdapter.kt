package com.example.peeringbyakugan

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.peeringbyakugan.Network.SearchOnlyResultsItem
import com.example.peeringbyakugan.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso

class AnimeRecyclerAdapter :
    ListAdapter<SearchOnlyResultsItem, AnimeRecyclerAdapter.ViewHolder>(AnimeInstanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animeTextView: TextView = itemView.findViewById(R.id.animeTitle)
        val animeImageView: ImageView = itemView.findViewById(R.id.animeImageView)


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemLayoutBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_layout, parent, false)
                return ViewHolder(binding.root)
            }
        }

        fun bind(item: SearchOnlyResultsItem) {

            Picasso.get().load(item.imageUrl).into(animeImageView)
//            animeImageView.setImageResource(R.drawable.test)
            animeTextView.text = item.title
        }
    }

}


class AnimeInstanceDiffCallback : DiffUtil.ItemCallback<SearchOnlyResultsItem>() {
    override fun areItemsTheSame(oldItem: SearchOnlyResultsItem, newItem: SearchOnlyResultsItem): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(oldItem: SearchOnlyResultsItem, newItem: SearchOnlyResultsItem): Boolean {
        return oldItem == newItem
    }


}