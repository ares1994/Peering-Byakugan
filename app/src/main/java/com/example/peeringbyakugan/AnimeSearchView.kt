package com.example.peeringbyakugan



import android.content.Context
import androidx.appcompat.widget.SearchView
import android.util.AttributeSet

class AnimeSearchView : SearchView {

    /*
* Created by: Jens Klingenberg (jensklingenberg.de)
*
*
*   //This SearchView gets triggered even when the query submit is empty
*
* */

    lateinit var mSearchSrcTextView: SearchAutoComplete
    internal var listener: OnQueryTextListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        mSearchSrcTextView = this.findViewById(androidx.appcompat.R.id.search_src_text)
        mSearchSrcTextView.setOnEditorActionListener { textView, i, keyEvent ->
            listener?.onQueryTextSubmit(query.toString())
            true
        }
    }
}