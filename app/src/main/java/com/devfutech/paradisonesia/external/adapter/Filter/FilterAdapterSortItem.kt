package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.domain.model.product.Product
import timber.log.Timber

class FilterAdapterSortItem(
    private val onItemSelected: (Int) -> Unit,
) : RecyclerView.Adapter<FilterAdapterSortItem.ViewHolder>() {

    var id : Int = 1
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

        holder.rg_sort_filter.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            if(holder.radB_lowest_price.isChecked){
                id = 1
            }
            if (holder.radB_highest_price.isChecked){
                id = 2
            }
            if(holder.radB_highest_rating.isChecked){
                id = 3
            }
            onItemSelected(id)

        })


        //holder.rg_sort_filter.setOnClickListener{

        //}
        //itemSelected.add(holder.rg_sort_filter.checkedRadioButtonId)
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rg_sort_filter : RadioGroup = itemView.findViewById(R.id.rg_sort_filter)
        val radB_lowest_price : RadioButton = itemView.findViewById(R.id.radB_lowest_price)
        val radB_highest_price : RadioButton = itemView.findViewById(R.id.radB_highest_price)
        val radB_highest_rating : RadioButton = itemView.findViewById(R.id.radB_highest_rating)
    }

    override fun getItemCount(): Int {

        return 1
    }
}