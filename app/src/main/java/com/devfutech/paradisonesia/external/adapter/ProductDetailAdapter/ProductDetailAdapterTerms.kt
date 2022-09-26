package com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.get
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductDetailFaqBinding
import com.devfutech.paradisonesia.databinding.ItemProductDetailIncludeExcludeBinding
import com.devfutech.paradisonesia.databinding.ItemProductDetailTermsBinding
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import timber.log.Timber

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapterTerms : ListAdapter<ProductDetail.Terms, ProductDetailAdapterTerms.ProductViewHolder>(
    POST_COMPARATOR
) {
    var i = 0
    inner class ProductViewHolder(private val binding: ItemProductDetailTermsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Terms) {
            binding.apply {
                if(i==itemCount){
                    i=0
                }
                i++
                tvTerms.text = this@ProductViewHolder.itemView.context.getString(R.string.terms, i, item.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductDetailTermsBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Terms>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Terms,
                newItem: ProductDetail.Terms
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Terms,
                newItem: ProductDetail.Terms
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}