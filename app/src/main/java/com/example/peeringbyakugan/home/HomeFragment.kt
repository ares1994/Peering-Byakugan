package com.example.peeringbyakugan.home


import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.peeringbyakugan.*
import com.example.peeringbyakugan.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeRecyclerAdapter


    @Inject
    lateinit var cm: ConnectivityManager


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val appComponent = ((this.activity!!.application) as ByakuganApplication).getAppComponent()
        appComponent.inject(this)
        Log.d("HomeFragment", "OnCreate called")
        (activity as AppCompatActivity).title = ""
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModelFactory =
            HomeViewModelFactory(appComponent)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)




        animeAdapter =
            AnimeRecyclerAdapter(AnimeClickListener { animeId, animeTitle ->
                this.findNavController()
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                            animeId,
                            animeTitle
                        )
                    )
            })

        binding.animeListRecyclerView.apply {
            adapter = animeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }

        viewModel.currentAnimeList.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                binding.errorView.visibility = View.VISIBLE
                binding.errorTextView.text = getString(R.string.none_found_error_message)
            } else {
                binding.errorView.visibility = View.INVISIBLE
            }
            animeAdapter.submitList(it)
            viewModel.progressBarInvisible()
        })


        viewModel.animeRetrievalAttemptCompleted.observe(this, Observer {
            if (it) {
                binding.animeListProgressBar.visibility = View.INVISIBLE
            } else {
                binding.animeListProgressBar.visibility = View.VISIBLE
            }
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

                if (cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected) {
                    val genreList = checkChipGroup()
                    if (query.isNullOrBlank() && genreList.isBlank()) {
                        Snackbar.make(searchView, "Enter search and/or select genres", Snackbar.LENGTH_LONG).show()
                        searchView.clearFocus()
                        return false
                    }

                    binding.errorView.visibility = View.INVISIBLE
                    animeAdapter.submitList(null)
                    viewModel.progressBarVisible()
                    viewModel.queryJikanSearchAndFilter(query!!, genreList)
                    searchView.clearFocus()
                    return true
                }
                binding.errorTextView.text = getString(R.string.error_internet)
                binding.errorView.visibility = View.VISIBLE
                animeAdapter.submitList(null)
                return true


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
