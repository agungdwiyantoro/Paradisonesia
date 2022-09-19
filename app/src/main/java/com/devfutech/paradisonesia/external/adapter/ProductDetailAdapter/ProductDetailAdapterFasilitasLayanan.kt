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
class ProductDetailAdapterFasilitasLayanan : ListAdapter<ProductDetail.Facilities, ProductDetailAdapterFasilitasLayanan.ProductViewHolder>(
    POST_COMPARATOR
) {

    inner class ProductViewHolder(private val binding: ItemProductDetailIncludeExcludeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Facilities) {
            binding.apply {
                if(item.facility.get(0)?.id==1){
                    ivCheckCircle.setImageResource(R.drawable.ic_baseline_wifi_24)
                    tvIncludeExclude.text = item.facility.get(0)?.name
                }
                if(item.facility.get(0)?.id==2){
                    ivCheckCircle.setImageResource(R.drawable.ic_baseline_restaurant_24)
                    tvIncludeExclude.text = item.facility.get(0)?.name
                }
                if(item.facility.get(0)?.id==3){
                    ivCheckCircle.setImageResource(R.drawable.ic_baseline_free_breakfast_24)
                    tvIncludeExclude.text = item.facility.get(0)?.name
                }
                if(item.facility.get(0)?.id==4){
                    ivCheckCircle.setImageResource(R.drawable.ic_baseline_ac_unit_24)
                    tvIncludeExclude.text = item.facility.get(0)?.name
                }
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Facilities>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Facilities,
                newItem: ProductDetail.Facilities
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Facilities,
                newItem: ProductDetail.Facilities
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}