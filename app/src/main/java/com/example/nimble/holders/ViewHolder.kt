//package com.example.nimble.holders
//
//import android.R
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.nimble.adapters.ProductsAdapter
//
//
//class MyViewHolder(v: View) : View.OnClickListener {
//    var img: ImageView
//    var nameTxt: TextView
//    var itemClickListener: ProductsAdapter.onItemClickListener? = null
//    override fun onClick(v: View) {
//        itemClickListener!!.onItemClick(v)
//    }
//
//    fun setItemClickListener(ic: ProductsAdapter.onItemClickListener?) {
//        itemClickListener = ic
//    }
//
//    init {
//        img = v.findViewById<View>(R.id.movieImage) as ImageView
//        nameTxt = v.findViewById<View>(R.id.nameTxt) as TextView
//        v.setOnClickListener(this)
//    }
//}