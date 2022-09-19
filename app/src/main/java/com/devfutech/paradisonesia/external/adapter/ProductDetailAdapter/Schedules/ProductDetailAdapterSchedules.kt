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
import com.devfutech.paradisonesia.databinding.ItemSchedulesHariKeTitleBinding
import com.devfutech.paradisonesia.databinding.LayoutProductDetailSchedulesBinding
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapterSchedules(adter: ProductDetailAdapterSchedulesDays) : ListAdapter<ProductDetail.Schedules, ProductDetailAdapterSchedules.ProductViewHolder>(
    POST_COMPARATOR
) {

    val ax = adter
    inner class ProductViewHolder(private val binding: ItemSchedulesHariKeTitleBinding, ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Schedules) {
            binding.apply {
                tvHariKe.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.hari_ke, item.order)
                tvSchedulesTitle.text = item.title
                if(item.days?.isEmpty() == false) {
                    rvProductDetailSchedules.adapter = ax
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemSchedulesHariKeTitleBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Schedules>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Schedules,
                newItem: ProductDetail.Schedules
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Schedules,
                newItem: ProductDetail.Schedules
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}