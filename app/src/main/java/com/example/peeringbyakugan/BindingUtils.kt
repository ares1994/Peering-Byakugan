package com.example.peeringbyakugan

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso




@BindingAdapter("setAnimeTitle")
fun TextView.setAnimeTitle(anime: SearchOnlyResultsItem) {
    anime.let {
        text = anime.title
    }
}

@BindingAdapter("setAnimeImage")
fun ImageView.setAnimeImage(anime: SearchOnlyResultsItem) {
    anime.let {
        Picasso.get().load(anime.imageUrl).into(this)
    }
}

@BindingAdapter("attachPopMenu")
fun TextView.attachPopMenu(anime: SearchOnlyResultsItem) {
    anime.let { animeInstance ->

        if (animeInstance.airing == false) this.visibility = View.GONE
        else {
            this.visibility = View.VISIBLE
        }
        this.setOnClickListener {

            val popup = PopupMenu(it.context, it)
            popup.inflate(R.menu.bookmark_popup_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.action_bookmark) {
                    Snackbar.make(it, "Successfully bookmarked", Snackbar.LENGTH_LONG).show()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
            popup.show()
        }
    }
}