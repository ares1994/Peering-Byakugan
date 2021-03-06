package com.arepadeobiri.peeringbyakugan.characterDetails


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arepadeobiri.peeringbyakugan.ByakuganApplication
import com.arepadeobiri.peeringbyakugan.GenericViewModelFactory

import com.arepadeobiri.peeringbyakugan.R
import com.arepadeobiri.peeringbyakugan.databinding.FragmentCharacterDetailsBinding
import com.arepadeobiri.peeringbyakugan.databinding.FragmentCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import java.lang.Exception

class CharacterDetails : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val args = arguments?.let { CharacterDetailsArgs.fromBundle(it) }

        activity!!.title = args!!.characterName


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false)

        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CharacterDetailsViewModel::class.java)

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


        viewModel.getCharacterDetails(args.characterId)

        viewModel.characterDetails.observe(this, Observer {
            if (it == null) {
                binding.characterImageView.visibility = View.INVISIBLE
                binding.characterDetailsCardView.visibility = View.INVISIBLE
                binding.errorView.visibility = View.VISIBLE
                return@Observer
            }
            binding.characterDetailsCardView.visibility = View.VISIBLE
            viewModel.picasso.apply {
                load(it.imageUrl).into(binding.characterImageView, object : Callback {
                    override fun onSuccess() {
                        binding.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, "Failed to load Image", Snackbar.LENGTH_LONG)
                            .show()
                    }
                })
            }
            binding.characterAbout.text = getString(R.string.character_about, it.about)


            var nicknames = ""
            if (it.nicknames.isNullOrEmpty()) nicknames = "N/A" else {

                it.nicknames.forEachIndexed { index, string ->
                    nicknames += string
                    if (index != it.nicknames.size - 1) nicknames += ", "

                }
            }
            binding.nicknameTextView.text = getString(R.string.nickname, nicknames)

            binding.kanjiTextView.text = getString(R.string.kanji, it.nameKanji)

        })




        return binding.root
    }


}
