package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R

class FilterAdapterSortItem(
    private val onItemSelected: (List<Int>) -> Unit,
) : RecyclerView.Adapter<FilterAdapterSortItem.ViewHolder>() {

    private val itemSelected = mutableListOf<Int>()
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_sort_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the image to the imageview from our itemHolder class


        // sets the text to the textview from our itemHolder class
        holder.radB_lowest_price.text = holder.itemView.resources.getString(R.string.lowest_price)
        holder.radB_highest_price.text = holder.itemView.resources.getString(R.string.highest_price)
        holder.radB_highest_rating.text = holder.itemView.resources.getString(R.string.highest_rating)

        onItemSelected(itemSelected)
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val radB_lowest_price : TextView = itemView.findViewById(R.id.radB_lowest_price)
        val radB_highest_price : TextView = itemView.findViewById(R.id.radB_highest_price)
        val radB_highest_rating : TextView = itemView.findViewById(R.id.radB_highest_rating)
    }

    override fun getItemCount(): Int {

        return 1
    }
}