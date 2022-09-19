package com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.Schedules

import android.view.LayoutInflater
import android.view.ViewGroup
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductDetailSchedulesBinding
import com.devfutech.paradisonesia.databinding.ItemSchedulesBinding
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapterSchedulesDays : ListAdapter<ProductDetail.Schedules.Days, ProductDetailAdapterSchedulesDays.ProductViewHolder>(
    POST_COMPARATOR
) {

    inner class ProductViewHolder(private val binding: ItemSchedulesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Schedules.Days) {
            binding.apply {
                tvTimeSchedules.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.time_schedules, item.start_time, item.end_time)
                tvDescriptionSchedules.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
           ItemSchedulesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Schedules.Days>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Schedules.Days,
                newItem: ProductDetail.Schedules.Days
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Schedules.Days,
                newItem: ProductDetail.Schedules.Days
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}