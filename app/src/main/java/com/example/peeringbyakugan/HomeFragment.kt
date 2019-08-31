package com.example.peeringbyakugan


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.peeringbyakugan.Network.Network
import com.example.peeringbyakugan.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Dispatchers


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

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
            layoutManager = GridLayoutManager(this.context, 2)
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
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            animeAdapter.submitList(null)
            binding.animeListProgressBar.visibility = View.VISIBLE
            viewModel.queryJikanSearchOnly(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}
