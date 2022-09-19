package com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductDetailIncludeExcludeBinding
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import timber.log.Timber

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapterIncludes : ListAdapter<ProductDetail.Include_Excludes, ProductDetailAdapterIncludes.ProductViewHolder>(
    POST_COMPARATOR
) {

    inner class ProductViewHolder(private val binding: ItemProductDetailIncludeExcludeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Include_Excludes) {
            binding.apply {
                if(item.is_include==0)ivCheckCircle.setImageResource(R.drawable.ic_baseline_check_circle_24) else ivCheckCircle.setImageResource(R.drawable.ic_baseline_cancel_24)
                tvIncludeExclude.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductDetailIncludeExcludeBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Include_Excludes>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Include_Excludes,
                newItem: ProductDetail.Include_Excludes
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Include_Excludes,
                newItem: ProductDetail.Include_Excludes
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}