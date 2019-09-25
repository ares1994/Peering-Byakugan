package com.example.peeringbyakugan.favourites


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
import com.example.peeringbyakugan.ByakuganApplication
import com.example.peeringbyakugan.GenericViewModelFactory

import com.example.peeringbyakugan.R
import com.example.peeringbyakugan.bookmark.BookmarkAnimeClickListener
import com.example.peeringbyakugan.bookmark.BookmarkAnimeRecyclerAdapter
import com.example.peeringbyakugan.databinding.FragmentFavouritesBinding
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
