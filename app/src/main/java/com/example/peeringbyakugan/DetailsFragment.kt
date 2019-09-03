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
import com.example.peeringbyakugan.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        (activity as AppCompatActivity).supportActionBar!!.hide()

        binding.youtubeWebView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            loadUrl("https://www.youtube.com/embed/j2hiC9BmJlQ?enablejsapi=1&wmode=opaque&autoplay=1")

            //loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/K4hy46Y-Cf8?enablejsapi=1&wmode=opaque&autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>","text/html","utf-8")
        }

        return binding.root
    }


}
