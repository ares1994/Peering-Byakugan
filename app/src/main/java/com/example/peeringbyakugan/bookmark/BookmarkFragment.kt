package com.example.peeringbyakugan.bookmark


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.peeringbyakugan.ByakuganApplication
import com.example.peeringbyakugan.GenericViewModelFactory

import com.example.peeringbyakugan.R
import com.example.peeringbyakugan.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var viewModel: BookmarkViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.activity!!.title = "Bookmarks"
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)
        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookmarkViewModel::class.java)



        return binding.root
    }


}
