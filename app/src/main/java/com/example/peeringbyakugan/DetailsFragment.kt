package com.example.peeringbyakugan


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.peeringbyakugan.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val args = arguments?.let { DetailsFragmentArgs.fromBundle(it) }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.queryJikanForAnime(args!!.animeId)
        binding.youtubeWebView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = AnimeWebViewClient(binding.trailerLoadingProgressBar)


        }

        viewModel.currentAnime.observe(this, Observer {
            if (it.trailerUrl.isNullOrBlank()) {
                binding.youtubeWebView.visibility = View.GONE
                binding.animeImageView.apply {
                    visibility = View.VISIBLE
                    Picasso.get().load(it.imageUrl).into(this)
                    binding.trailerLoadingProgressBar.visibility = View.INVISIBLE
                }
            } else {
                binding.youtubeWebView.loadUrl(it.trailerUrl)
            }
            if (it.titleEnglish.isNullOrBlank()) {
                (activity as AppCompatActivity).title = it.title

            } else {
                (activity as AppCompatActivity).title = it.titleEnglish

            }


        })



        return binding.root
    }


}
