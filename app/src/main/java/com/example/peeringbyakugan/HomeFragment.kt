package com.example.peeringbyakugan


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.peeringbyakugan.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        animeAdapter = AnimeRecyclerAdapter()

        binding.animeListRecyclerView.apply {
            adapter = animeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.currentAnimeList.observe(this, Observer {
            animeAdapter.submitList(it)
            binding.animeListProgressBar.visibility = View.INVISIBLE
        })


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_fragment_menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as AnimeSearchView
        searchView.queryHint = getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val genreList = checkChipGroup()
                if (query.isNullOrBlank() && genreList.isBlank()) {
                    Snackbar.make(searchView, "Enter search and/or select genres", Snackbar.LENGTH_LONG).show()
                    searchView.clearFocus()
                    return true
                }

                animeAdapter.submitList(null)
                binding.animeListProgressBar.visibility = View.VISIBLE
                viewModel.queryJikanSearchAndFilter(query!!, genreList)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        return true
//    }

    fun checkChipGroup(): String {
        var genreList = ""
        val chipNo = binding.filterChipGroup.childCount
        for (x in 1..chipNo) {
            val chip = binding.filterChipGroup.getChildAt(x - 1) as Chip
            if (chip.isChecked) {
                genreList += "$x,"
            }
        }

        return genreList

    }


}
