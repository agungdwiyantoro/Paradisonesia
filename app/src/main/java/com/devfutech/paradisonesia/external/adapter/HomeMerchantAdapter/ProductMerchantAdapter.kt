package com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemMerchantTransactionBinding
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber


class ProductMerchantAdapter(
    private val list: MutableList<HomeMerchant.MerchantProduct>,
) : RecyclerView.Adapter<ProductMerchantAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_merchant_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitleProductMerchant.text = list.get(position).productName
        holder.tvProductServiceName.text = list.get(position).productType
        holder.tvProductAddress.text = list.get(position).productAddress
        holder.tvTotal.text = FileUtils.convertToCurrency(list.get(position).productTotal)
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTitleProductMerchant : AppCompatTextView = itemView.findViewById(R.id.tv_item_title)
        val tvProductServiceName : AppCompatTextView = itemView.findViewById(R.id.tv_service_name)
        val tvProductAddress : AppCompatTextView = itemView.findViewById(R.id.tv_address)
        val tvTotal : AppCompatTextView = itemView.findViewById(R.id.tv_total)
    }

    override fun getItemCount(): Int {

        return list.size
    }
}