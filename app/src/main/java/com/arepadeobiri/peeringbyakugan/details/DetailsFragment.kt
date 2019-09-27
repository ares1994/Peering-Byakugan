package com.arepadeobiri.peeringbyakugan.details


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.arepadeobiri.peeringbyakugan.AnimeWebViewClient
import com.arepadeobiri.peeringbyakugan.ByakuganApplication
import com.arepadeobiri.peeringbyakugan.R
import com.arepadeobiri.peeringbyakugan.databinding.FragmentDetailsBinding
import com.arepadeobiri.peeringbyakugan.GenericViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import java.lang.Exception


class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val args = arguments?.let { DetailsFragmentArgs.fromBundle(it) }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details, container, false
        )
        binding.lifecycleOwner = this

        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {binding.errorImageView.setImageResource(R.drawable.nightmode_error)}
            Configuration.UI_MODE_NIGHT_NO -> {binding.errorImageView.setImageResource(R.drawable.error)}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {binding.errorImageView.setImageResource(R.drawable.error)}
        }


        viewModel.queryJikanForAnime(args!!.animeId)

        binding.youtubeWebView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = AnimeWebViewClient(binding.trailerLoadingProgressBar)
        }

        viewModel.currentAnime.observe(this, Observer {

            if (it == null) {
                binding.apply {
                    youtubeWebView.visibility = View.INVISIBLE
                    animeImageView.visibility = View.INVISIBLE
                    trailerLoadingProgressBar.visibility = View.INVISIBLE
                    synopsisCardView.visibility = View.INVISIBLE
                    errorView.visibility = View.VISIBLE
                    setMenuVisibility(false)
                }
                return@Observer
            }

            binding.synopsisCardView.visibility = View.VISIBLE
            binding.premierDateTextView.text =
                if (it.premiered.isNullOrBlank()) getString(
                    R.string.premier_date,
                    it.aired!!.string!!.split(Regex("to"))[0]
                )
                else {
                    getString(R.string.premier_date, it.premiered)
                }
            binding.ratingsTextView.text = if (it.score.toString() == "null") getString(
                R.string.ratings,
                getString(R.string.not_applicable)
            )
            else getString(R.string.ratings, it.score.toString())

            if (it.episodes.toString() == "null") {
                binding.episodesTextView.text =
                    getString(R.string.episodes, getString(R.string.not_applicable))
            } else {
                binding.episodesTextView.text = getString(R.string.episodes, it.episodes.toString())
            }

            binding.statusTextView.text = getString(R.string.status, it.status)


            if (it.trailerUrl.isNullOrBlank()) {
                binding.youtubeWebView.visibility = View.INVISIBLE
                binding.animeImageView.apply {
                    visibility = View.VISIBLE
                    viewModel.picasso.load(it.imageUrl).into(this, object : Callback {
                        override fun onSuccess() {
                            binding.trailerLoadingProgressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            binding.trailerLoadingProgressBar.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                "Failed to load Image",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                    })
                }
            } else {
                binding.youtubeWebView.loadUrl(it.trailerUrl)
            }

            binding.synopsisTextView.text =
                if (it.synopsis.isNullOrBlank()) getString(
                    R.string.synopsis,
                    getString(R.string.not_applicable)
                )
                else getString(R.string.synopsis, it.synopsis)

            (activity as AppCompatActivity).apply {
                if (title.isNullOrBlank()) title = it.titleEnglish
            }


        })

        viewModel.currentResponse.observe(this, Observer {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })



        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_characters -> {
                if (viewModel.isInternetConnection()) {
                    val args = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
                    this.findNavController()
                        .navigate(DetailsFragmentDirections.Characters(args!!.animeId))
                } else Snackbar.make(
                    binding.root,
                    getString(R.string.error_internet),
                    Snackbar.LENGTH_LONG
                ).show()

                return true
            }

            R.id.action_favourite -> {
                viewModel.favouriteAnimeToDatabase()


            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Log.d("DetailsFragment", "OnResumeCalled")
        val args = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        (activity as AppCompatActivity).title = args?.animeTitle
    }

}
