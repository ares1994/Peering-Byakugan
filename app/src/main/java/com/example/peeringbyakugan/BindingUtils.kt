package com.example.peeringbyakugan

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.peeringbyakugan.network.SearchOnlyResultsItem
import com.squareup.picasso.Picasso


@BindingAdapter("setAnimeTitle")
fun TextView.setAnimeTitle(anime: SearchOnlyResultsItem){
    anime.let {
        text = anime.title
    }
}

@BindingAdapter("setAnimeImage")
fun ImageView.setAnimeImage(anime: SearchOnlyResultsItem){
    anime.let {
        Picasso.get().load(anime.imageUrl).into(this)
    }
}