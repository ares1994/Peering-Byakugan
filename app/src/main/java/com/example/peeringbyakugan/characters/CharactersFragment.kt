package com.example.peeringbyakugan.characters


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.peeringbyakugan.AnimeClickListener
import com.example.peeringbyakugan.AnimeRecyclerAdapter
import com.example.peeringbyakugan.ByakuganApplication

import com.example.peeringbyakugan.R
import com.example.peeringbyakugan.databinding.FragmentCharactersBinding
import com.example.peeringbyakugan.GenericViewModelFactory


class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var animeAdapter: AnimeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_characters, container, false
        )

        binding.lifecycleOwner = this
        val args = arguments?.let { CharactersFragmentArgs.fromBundle(it) }

        val viewModelFactory =
            GenericViewModelFactory(((this.activity!!.application) as ByakuganApplication).getAppComponent())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)


        animeAdapter =
            AnimeRecyclerAdapter(AnimeClickListener { characterId, characterName ->
                Log.d("CharactersFragment", "Character Clicked is $characterName")
                this.findNavController().navigate(
                    CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(
                        characterName,
                        characterId
                    )
                )

            })

        binding.characterRecyclerView.apply {
            adapter = animeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }
        viewModel.queryJikanForAnimeCharacters(args!!.animeId)

        viewModel.currentAnimeCharacterList.observe(this, Observer {
            animeAdapter.submitList(it)
        })


        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).title = getString(R.string.characters)
    }
}
