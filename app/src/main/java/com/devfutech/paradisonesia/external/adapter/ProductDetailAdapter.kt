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
import com.devfutech.paradisonesia.databinding.ProductDetailFragmentBinding
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.product.ProductParcelable
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.external.utils.FileUtils.convertToCurrency
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.presentation.fragments.product.ProductDetailFragmentArgs
import com.devfutech.paradisonesia.presentation.fragments.product.ProductFragment
import com.devfutech.paradisonesia.presentation.fragments.product.ProductFragmentDirections
import timber.log.Timber

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapter : ListAdapter<ProductDetail, ProductDetailAdapter.ProductViewHolder>(POST_COMPARATOR) {

    inner class ProductViewHolder(private val binding: ProductDetailFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail) {
            binding.apply {
                ivProduct.loadAny(item.thumbnail?:""){
                    crossfade(true)
                    error(R.drawable.object_wisata_lombok)
                    //error(R.drawable.ic_image_not_available)
                }

                Timber.tag("PDA").d("PDAs" + item.wishlist_count)
               // tvDetailCategoryProduct.text = item.sub_category?.name
                tvDetailProductRating.text = item.rating_average
                tvDescription.text = item.description
                tvDetailProductFavorit.text = item.wishlist_count.toString()


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

                val action = ProductFragmentDirections.actionProductFragmentToProductDetailFragment(productParcelable)
                root.setOnClickListener{
                    findNavController(it).navigate(action)
                }

                 */

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductDetailFragmentBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail,
                newItem: ProductDetail
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail,
                newItem: ProductDetail
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}