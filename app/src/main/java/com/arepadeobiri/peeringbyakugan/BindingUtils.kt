package com.arepadeobiri.peeringbyakugan

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.arepadeobiri.peeringbyakugan.Util.Companion.BOOKMARK_TYPE
import com.arepadeobiri.peeringbyakugan.Util.Companion.DATABASE_RESPONSE
import com.arepadeobiri.peeringbyakugan.Util.Companion.FAVOURITE_TYPE
import com.arepadeobiri.peeringbyakugan.Util.Companion.PREF_NAME
import com.arepadeobiri.peeringbyakugan.database.DatabaseAnime
import com.arepadeobiri.peeringbyakugan.database.getDatabase
import com.arepadeobiri.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private val job = Job()
private val scope = CoroutineScope(Dispatchers.Main + job)

private var animeRepo: AnimeRepository? = null

private var pref: SharedPreferences? = null


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

@BindingAdapter("setBookmarkAnimeTitle")
fun TextView.setAnimeTitle(anime: DatabaseAnime) {
    anime.let {
        text = anime.title
    }
}

@BindingAdapter("setBookmarkAnimeImage")
fun ImageView.setAnimeImage(anime: DatabaseAnime) {
    anime.let {
        Picasso.get().load(anime.imageUrl).into(this)
    }
}

@BindingAdapter("removeAnimePopMenu")
fun TextView.removeAnimePopMenu(anime: DatabaseAnime) {
    pref = this.context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    animeRepo =
        AnimeRepository(getDatabase(this.context.applicationContext).animeDao, pref!!)
    anime.let { animeInstance ->


        this.setOnClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.inflate(R.menu.remove_bookmarks_favourites_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when {
                    menuItem.itemId == R.id.action_remove -> {
                        scope.launch {
                            animeRepo!!.removeAnime(animeInstance)
                        }
                        return@setOnMenuItemClickListener true
                    }
                }

                return@setOnMenuItemClickListener false
            }
            popupMenu.show()
        }
    }
}


@BindingAdapter("attachPopMenu")
fun TextView.attachPopMenu(anime: SearchOnlyResultsItem) {
    pref = this.context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    animeRepo =
        AnimeRepository(getDatabase(this.context.applicationContext).animeDao, pref!!)

    anime.let { animeInstance ->

        animeInstance.apply {
            when {
                airing == false && synopsis == null && startDate == null -> this@attachPopMenu.visibility =
                    View.GONE
            }
        }

        this.setOnClickListener {

            val popup = PopupMenu(it.context, it)
            when {
                animeInstance.airing == false -> popup.inflate(R.menu.favourite_popup_menu)
                else -> popup.inflate(R.menu.bookmark_favourite_popup_menu)
            }

            popup.setOnMenuItemClickListener { menuItem ->
                when {
                    menuItem.itemId == R.id.action_bookmark -> {

                        scope.launch {
                            animeRepo!!.saveDatabaseAnime(
                                DatabaseAnime(
                                    animeInstance.malId!!,
                                    animeInstance.title!!,
                                    animeInstance.airing!!,
                                    animeInstance.startDate!!,
                                    animeInstance.imageUrl!!,
                                    BOOKMARK_TYPE,
                                    System.currentTimeMillis()
                                )
                            )
                            Snackbar.make(
                                it,
                                pref!!.getString(DATABASE_RESPONSE, "").toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        return@setOnMenuItemClickListener true
                    }
                    menuItem.itemId == R.id.action_favourite -> {
                        scope.launch {
                            animeRepo!!.saveDatabaseAnime(
                                DatabaseAnime(
                                    animeInstance.malId!!,
                                    animeInstance.title!!,
                                    animeInstance.airing!!,
                                    animeInstance.startDate!!,
                                    animeInstance.imageUrl!!,
                                    FAVOURITE_TYPE,
                                    System.currentTimeMillis()
                                )
                            )
                            Snackbar.make(
                                it,
                                pref!!.getString(DATABASE_RESPONSE, "").toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.show()
        }
    }

}