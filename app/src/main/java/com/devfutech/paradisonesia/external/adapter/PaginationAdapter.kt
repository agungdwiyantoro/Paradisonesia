package com.devfutech.paradisonesia.external.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import com.devfutech.paradisonesia.presentation.fragments.product.ProductViewModel
import timber.log.Timber

class PaginationAdapter(private val index: List<Int>, private val viewModel: ProductViewModel, tvResult: AppCompatTextView, context: Context
) : RecyclerView.Adapter<PaginationAdapter.ViewHolder>() {


    val tempContext = context
    val tempTvResult = tvResult
    val tempViewModel = viewModel
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pagination, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvPagination.text = index.get(position).digitToChar().toString()

        if(holder.tvPagination.isFocused){
            holder.tvPagination.text = "JLSDJD"
        }

        holder.tvPagination.setOnClickListener{
            FilterBottomSheet.map += mapOf(
                "show" to "10",
                "page" to (position+1).toString()
            )
            tempViewModel.getProductAllSearch(FilterBottomSheet.map, tempTvResult, tempContext)
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvPagination : AppCompatTextView = itemView.findViewById(R.id.tv_pagination)
    }

    override fun getItemCount(): Int {

        return index.size
    }
}