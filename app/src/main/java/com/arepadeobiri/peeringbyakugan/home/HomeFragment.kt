package com.arepadeobiri.peeringbyakugan.home


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arepadeobiri.peeringbyakugan.*
import com.arepadeobiri.peeringbyakugan.Util.Companion.NIGHT_MODE_STATE
import com.arepadeobiri.peeringbyakugan.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*


class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener,
    SeekBar.OnSeekBarChangeListener {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeRecyclerAdapter
    private val bundle: Bundle? = Bundle()
    private var genreList: String = ""


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.errorImageView.setImageResource(R.drawable.nightmode_error)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.errorImageView.setImageResource(R.drawable.error)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                binding.errorImageView.setImageResource(R.drawable.error)
            }
        }


        val header = ((activity as MainActivity).navView as NavigationView).getHeaderView(0)
        header.daySpinner.adapter =
            SpinnerAdapter(this.activity!!.application, HomeViewModel.daysList)
        header.daySpinner.onItemSelectedListener = this
        header.scoreSeekBar.setOnSeekBarChangeListener(this)
        header.orderBySpinner.adapter =
            SpinnerAdapter(this.activity!!.application, HomeViewModel.orderList)

        val state = viewModel.pref.getBoolean(NIGHT_MODE_STATE, false)
        if (state) {
            header.nightModeSwitch.isChecked = state
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            
        } else {
            header.nightModeSwitch.isChecked = state
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        header.nightModeSwitch.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("Ares", "listener called")
            if (b) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                viewModel.pref.edit().putBoolean(NIGHT_MODE_STATE, true).apply()
            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.pref.edit().putBoolean(NIGHT_MODE_STATE, false).apply()
            }
        }





        animeAdapter =
            AnimeRecyclerAdapter(AnimeClickListener { animeId, animeTitle ->
                if (viewModel.isInternetConnection()) {
                    this.findNavController()
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                animeId,
                                animeTitle
                            )
                        )
                } else {
                    internetErrorMechanism()
                }

            })

        binding.animeListRecyclerView.apply {
            adapter = animeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {

                        val score = viewModel.seekBarValue.value.toString()
                        val orderSelected =
                            HomeViewModel.orderListBundle.getString(HomeViewModel.orderList[header.orderBySpinner.selectedItemPosition])


                        if (viewModel.scheduleOrQuery.value!!) {
                            binding.loadingMoreProgressBar.visibility = View.VISIBLE

                            Log.d(
                                "Ares",
                                "page= ${viewModel.page} and basePage = ${viewModel.basePage}"
                            )
                            if (viewModel.page == viewModel.basePage) {
                                viewModel.queryJikanSearchAndFilter(
                                    viewModel.currentQuery.value!!,
                                    genreList,
                                    score,
                                    orderSelected!!
                                )
                            }
                        }
                    }
                }
            })

        }

        viewModel.animeRetrievalSuccessful.observe(this, Observer {
            if (!it) {
                binding.errorView.visibility = View.VISIBLE
                binding.errorTextView.text = getString(R.string.insufficient_internet_error)
            } else {
                binding.errorView.visibility = View.INVISIBLE
            }
        })

        viewModel.currentAnimeList.observe(this, Observer {
            binding.loadingMoreProgressBar.visibility = View.GONE
            if (it.isNullOrEmpty() && viewModel.page == 1) {
                binding.animeListRecyclerView.visibility = View.INVISIBLE
                binding.errorView.visibility = View.VISIBLE
                binding.errorTextView.text = getString(R.string.none_found_error_message)
            } else {
                binding.errorView.visibility = View.INVISIBLE
                binding.animeListRecyclerView.visibility = View.VISIBLE

            }
            Log.d("HomeFragment", "New animeList received")

            if (animeAdapter.currentList.isNullOrEmpty()) animeAdapter.submitList(it) else animeAdapter.submitList(
                animeAdapter.currentList.plus(it)
            )
            viewModel.progressBarInvisible()
        })


        viewModel.animeRetrievalAttemptCompleted.observe(this, Observer {
            if (it) {
                binding.animeListProgressBar.visibility = View.INVISIBLE
            } else {
                binding.animeListProgressBar.visibility = View.VISIBLE
            }
        })

        viewModel.seekBarValue.observe(this, Observer {
            header.currentScoreTextView.text = it.toString()
        })

        viewModel.resultsExhausted.observe(this, Observer {
            if (it == true) {
                binding.loadingMoreProgressBar.visibility = View.GONE
                viewModel.resetResultsExhausted()
            }

        })









        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_fragment_menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as AnimeSearchView
        val header = ((activity as MainActivity).navView as NavigationView).getHeaderView(0)


        searchView.queryHint = getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.page = 0
                viewModel.basePage = 0
                val drawerLayout: DrawerLayout =
                    (activity as MainActivity).findViewById(R.id.drawerLayout)
                drawerLayout.closeDrawers()

                if (viewModel.isInternetConnection()) {
                    viewModel.setCurrentQuery(query!!)
                    val genreList = checkChipGroup()
                    val score = viewModel.seekBarValue.value.toString()
                    val orderSelected =
                        HomeViewModel.orderListBundle.getString(HomeViewModel.orderList[header.orderBySpinner.selectedItemPosition])
                    if (query.isNullOrBlank() && genreList.isBlank() && score == "0.0" && orderSelected.isNullOrBlank()) {
                        Snackbar.make(
                            searchView,
                            "Enter search and/or select genres",
                            Snackbar.LENGTH_LONG
                        ).show()
                        searchView.clearFocus()
                        return false
                    }
                    loadingMechanism()
                    viewModel.queryJikanSearchAndFilter(
                        viewModel.currentQuery.value!!,
                        genreList,
                        score,
                        orderSelected!!
                    )
                    searchView.clearFocus()
                    animeAdapter.submitList(animeAdapter.currentList.apply { clear() })
                    return true
                }
                animeAdapter.submitList(animeAdapter.currentList.apply { clear() })
                internetErrorMechanism()
                return true


            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun internetErrorMechanism() {
        binding.errorTextView.text = getString(R.string.error_internet)
        binding.errorView.visibility = View.VISIBLE
        animeAdapter.submitList(null)
    }

    private fun loadingMechanism() {
        binding.errorView.visibility = View.INVISIBLE
        animeAdapter.submitList(null)
        viewModel.progressBarVisible()
    }

    fun checkChipGroup(): String {
        genreList = ""
        val chipNo = binding.filterChipGroup.childCount
        for (x in 1..chipNo) {
            val chip = binding.filterChipGroup.getChildAt(x - 1) as Chip
            if (chip.isChecked) {
                genreList += "$x,"
            }
        }



        return genreList

    }


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) return
        if (viewModel.isInternetConnection()) {
            loadingMechanism()
            val list = animeAdapter.currentList
            list.clear()
            animeAdapter.submitList(list)
            viewModel.queryJikanSchedule("${parent?.adapter?.getItem(position)}")
        } else {
            internetErrorMechanism()
        }
        val drawerLayout: DrawerLayout = (activity as MainActivity).findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawers()

    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        viewModel.setSeekBarValue((p1 / 10).toFloat())
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).title = ""
        Log.d("Ares", "onResume called")

    }

    override fun onPause() {
        super.onPause()
        val header = ((activity as MainActivity).navView as NavigationView).getHeaderView(0)
        bundle?.putInt("rating", header.scoreSeekBar.progress)
        bundle?.putInt("orderBy", header.orderBySpinner.selectedItemPosition)
        bundle?.putInt("pageNo", viewModel.basePage)
        bundle?.putString("genreList", genreList)
        val chipNo = binding.filterChipGroup.childCount
        val list: MutableList<Int> = mutableListOf()
        for (x in 1..chipNo) {
            val chip = binding.filterChipGroup.getChildAt(x - 1) as Chip
            if (chip.isChecked) {
                Log.d("Ares", x.toString())
                list.add(x)
            }
        }
        bundle?.putIntArray("checkedChips", list.toIntArray())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val header = ((activity as MainActivity).navView as NavigationView).getHeaderView(0)
        Log.d("Ares", "onViewCreated called")
        if (!bundle!!.isEmpty) {
            viewModel.basePage = bundle.getInt("pageNo", 0)
            header.orderBySpinner.setSelection(bundle.getInt("orderBy", 0))
            header.scoreSeekBar.progress = bundle.getInt("rating", 0)
            genreList = bundle.getString("genreList", "")
            val checkedList: IntArray? = bundle.getIntArray("checkedChips")
            checkedList?.forEach {
                val chip = binding.filterChipGroup.getChildAt(it - 1) as Chip
                chip.isChecked = true

            }
        }

    }


}
