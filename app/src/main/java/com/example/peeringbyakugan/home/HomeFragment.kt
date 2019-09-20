package com.example.peeringbyakugan.home


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.peeringbyakugan.*
import com.example.peeringbyakugan.Util.Companion.CHANNEL_ID
import com.example.peeringbyakugan.Util.Companion.notificationId
import com.example.peeringbyakugan.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*


class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeRecyclerAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Log.d("HomeFragment", "OnCreate called")



        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.animeRepo.animeList.observe(this, Observer {
            if (it.isNullOrEmpty()) return@Observer
            Toast.makeText(this.context, it[0].title, Toast.LENGTH_LONG).show()
        })


        val header = ((activity as MainActivity).navView as NavigationView).getHeaderView(0)
        header.daySpinner.adapter = SpinnerAdapter(this.activity!!.application, HomeViewModel.daysList)
        header.daySpinner.onItemSelectedListener = this
        header.scoreSeekBar.setOnSeekBarChangeListener(this)
        header.orderBySpinner.adapter = SpinnerAdapter(this.activity!!.application, HomeViewModel.orderList)



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

        viewModel.seekBarValue.observe(this, Observer {
            header.currentScoreTextView.text = it.toString()
        })

        providesNotificationManager()


        val builder = NotificationCompat.Builder(this.context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_search_black_24dp)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        binding.errorView.setOnClickListener {
            with(NotificationManagerCompat.from(this.context!!)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())

            }
        }



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

                if (viewModel.isInternetConnection()) {
                    val genreList = checkChipGroup()
                    val score = viewModel.seekBarValue.value.toString()
                    val orderSelected =
                        HomeViewModel.orderListBundle.getString(HomeViewModel.orderList[header.orderBySpinner.selectedItemPosition])
                    if (query.isNullOrBlank() && genreList.isBlank() && score == "0.0" && orderSelected.isNullOrBlank()) {
                        Snackbar.make(searchView, "Enter search and/or select genres", Snackbar.LENGTH_LONG).show()
                        searchView.clearFocus()
                        return false
                    }
                    loadingMechanism()
                    viewModel.queryJikanSearchAndFilter(query!!, genreList, score, orderSelected!!)
                    searchView.clearFocus()
                    header.scoreSeekBar.progress = 0
                    return true
                }
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


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) return
        if (viewModel.isInternetConnection()) {
            loadingMechanism()
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
    }

    fun providesNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.not_applicable)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                this.activity!!.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }


}
