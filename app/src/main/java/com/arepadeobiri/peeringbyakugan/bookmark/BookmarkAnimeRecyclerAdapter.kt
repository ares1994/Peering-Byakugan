package com.arepadeobiri.peeringbyakugan.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arepadeobiri.peeringbyakugan.R
import com.arepadeobiri.peeringbyakugan.database.DatabaseAnime
import com.arepadeobiri.peeringbyakugan.databinding.ItemBookmarkLayoutBinding



class BookmarkAnimeRecyclerAdapter(private val clickListener: BookmarkAnimeClickListener) :
    ListAdapter<DatabaseAnime, BookmarkAnimeRecyclerAdapter.ViewHolder>(BookmarkAnimeInstanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
        holder.bind(getItem(position), clickListener)

    }


    class ViewHolder private constructor(private val binding: ItemBookmarkLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemBookmarkLayoutBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_bookmark_layout, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: DatabaseAnime,
            clickListener: BookmarkAnimeClickListener

        ) {
//            Picasso.get().load(item.imageUrl).into(binding.animeImageView)
//            binding.animeTitle.text = item.title

            binding.clickListener = clickListener
            binding.bookmarkAnime = item
            binding.executePendingBindings()
        }
    }

}


class BookmarkAnimeInstanceDiffCallback : DiffUtil.ItemCallback<DatabaseAnime>() {
    override fun areItemsTheSame(oldItem: DatabaseAnime, newItem: DatabaseAnime): Boolean {
        return oldItem.malId == newItem.malId
    }

    override fun areContentsTheSame(oldItem: DatabaseAnime, newItem: DatabaseAnime): Boolean {
        return oldItem == newItem
    }


}


class BookmarkAnimeClickListener(val clickListener: (animeId: Int, animeTitle: String) -> Unit) {
    fun onClick(anime: DatabaseAnime) = clickListener(anime.malId, anime.title)
}
