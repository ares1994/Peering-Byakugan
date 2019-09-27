package com.arepadeobiri.peeringbyakugan.favourites


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arepadeobiri.peeringbyakugan.ByakuganApplication
import com.arepadeobiri.peeringbyakugan.GenericViewModelFactory

import com.arepadeobiri.peeringbyakugan.R
import com.arepadeobiri.peeringbyakugan.bookmark.BookmarkAnimeClickListener
import com.arepadeobiri.peeringbyakugan.bookmark.BookmarkAnimeRecyclerAdapter
import com.arepadeobiri.peeringbyakugan.databinding.FragmentFavouritesBinding
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var animeAdapter: BookmarkAnimeRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        this.activity!!.title = getString(R.string.favourites)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FavouritesViewModel::class.java)

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {binding.errorImageView.setImageResource(R.drawable.nightmode_error)}
            Configuration.UI_MODE_NIGHT_NO -> {binding.errorImageView.setImageResource(R.drawable.error)}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {binding.errorImageView.setImageResource(R.drawable.error)}
        }


        animeAdapter =
            BookmarkAnimeRecyclerAdapter(BookmarkAnimeClickListener { characterId, characterName ->
                if (viewModel.isInternetConnection()) {
                    this.findNavController().navigate(
                        FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(
                            characterId,
                            characterName
                        )
                    )
                } else Snackbar.make(
                    binding.root,
                    getString(R.string.error_internet),
                    Snackbar.LENGTH_LONG
                ).show()

            })

        binding.favouritesRecyclerView.apply {
            adapter = animeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }

        viewModel.animeRepo.favouritesAnimeList.observe(this, Observer {
            animeAdapter.submitList(it)
            if (it.isNullOrEmpty()) binding.errorView.visibility =
                View.VISIBLE else binding.errorView.visibility = View.INVISIBLE
        })



        return binding.root
    }


}
