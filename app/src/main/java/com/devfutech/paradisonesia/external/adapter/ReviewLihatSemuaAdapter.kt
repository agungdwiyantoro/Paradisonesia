package com.devfutech.paradisonesia.external.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.presentation.fragments.review_lihat_semua.ReviewLihatSemua
import com.devfutech.paradisonesia.presentation.fragments.review_lihat_semua.ReviewLihatSemuaViewModel
import timber.log.Timber

class ReviewLihatSemuaAdapter(private val mList: List<String>, private val index: Int, private val viewModel: ReviewLihatSemuaViewModel)
    : RecyclerView.Adapter<ReviewLihatSemuaAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_choose_star, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class


        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel
        Timber.tag("rvChooseStarAdapter").d("bitchass")
        holder.textView.setOnClickListener{
            viewModel.ProductReviewsFilter(index.toString(), (position+1).toString())
            Timber.tag("rvChooseStarAdapter").d("clicked " + (position+1))
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_star)
    }
}