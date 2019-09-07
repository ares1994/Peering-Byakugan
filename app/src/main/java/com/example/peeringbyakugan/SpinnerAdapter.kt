package com.example.peeringbyakugan


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.peeringbyakugan.databinding.ItemSpinnerBinding

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SpinnerAdapter(context: Context, list: List<String>) : ArrayAdapter<String>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }


    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemSpinnerBinding: ItemSpinnerBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_spinner, parent, false)
        itemSpinnerBinding.dayTextView.text = getItem(position)
        return itemSpinnerBinding.root
    }
}
