package com.arepadeobiri.peeringbyakugan


import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arepadeobiri.peeringbyakugan.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        activity!!.title = getString(R.string.about)

        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.imageView.setImageResource(R.drawable.nightmode_error)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.imageView.setImageResource(R.drawable.error)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                binding.imageView.setImageResource(R.drawable.error)
            }
        }


        return binding.root
    }


}
