package com.example.peeringbyakugan.bookmark


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.peeringbyakugan.ByakuganApplication
import com.example.peeringbyakugan.GenericViewModelFactory

import com.example.peeringbyakugan.R
import com.example.peeringbyakugan.Util
import com.example.peeringbyakugan.databinding.FragmentBookmarkBinding
import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    private lateinit var databaseAnimeAdapter: BookmarkAnimeRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.activity!!.title = getString(R.string.bookmarks)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)
        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookmarkViewModel::class.java)

        databaseAnimeAdapter =
            BookmarkAnimeRecyclerAdapter(BookmarkAnimeClickListener { animeId, animeTitle ->
                when {
                    viewModel.isInternetConnection() -> this.findNavController()
                        .navigate(
                            BookmarkFragmentDirections.actionBookmarkFragmentToDetailsFragment(
                                animeId,
                                animeTitle
                            )
                        )
                    else -> Snackbar.make(
                        binding.root,
                        getString(R.string.error_internet),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })


        binding.bookmarkRecyclerView.apply {
            adapter = databaseAnimeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.animeRepo.bookmarkAnimeList.observe(this, Observer {
            databaseAnimeAdapter.submitList(it)
        })





        return binding.root
    }


}
