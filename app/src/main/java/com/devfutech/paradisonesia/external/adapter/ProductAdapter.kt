package com.devfutech.paradisonesia.external.adapter

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductBinding
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.product.ProductParcelable
import com.devfutech.paradisonesia.external.utils.FileUtils.convertToCurrency
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.presentation.fragments.product.ProductDetailFragmentArgs
import com.devfutech.paradisonesia.presentation.fragments.product.ProductFragment
import com.devfutech.paradisonesia.presentation.fragments.product.ProductFragmentDirections

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(POST_COMPARATOR) {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.apply {
                ivProduct.loadAny(item.thumbnail?:""){
                    crossfade(true)
                    error(R.drawable.object_wisata_lombok)
                    //error(R.drawable.ic_image_not_available)
                }

                tvProductName.text = item.name

                tvProductFinalPrice.text =   this@ProductViewHolder.itemView.context.resources.getString(
                    R.string.final_price, convertToCurrency(item.price)
                )

                tvProductRating.text = item.rating_average

                tvProductReviews.text = this@ProductViewHolder.itemView.context.resources.getString(
                    R.string.reviews_w_value, item.reviews_count
                )

                tvProductDiscountedPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvProductDiscountedPrice.text = this@ProductViewHolder.itemView.context.resources.getString(
                    R.string.discounted_price, convertToCurrency(item.net_price)
                )

                //tvProductRating.text = item.
//                root.apply {
//                    setOnClickListener {
//                        findNavController().navigate(ShopFragmentDirections.actionShopFragmentToProductFragment(item))
//                    }
//                }

                /*



                val bundle  = Bundle()
                bundle.putString("name", item.name)
                bundle.putString("rating_average", item.rating_average)
                bundle.putString("reviews_count", item.reviews_count.toString())
                bundle.putString("reviews_count", item.description)

                val bundle = bundleOf("name", bundle)


                val productParcelable = ProductParcelable(
                    item.name,
                    item.coordinate,
                    item.rating_average,
                    item.reviews_count,
                    item.description,
                    item.sub_category?.name)

                 */

                val priceID = PriceID(
                    item.id,
                    item.price
                )

                val action = ProductFragmentDirections.actionProductFragmentToProductDetailFragment(priceID)
                root.setOnClickListener{
                    findNavController(it).navigate(action)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}