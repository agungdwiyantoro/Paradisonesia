package com.devfutech.paradisonesia.external.adapter

import android.content.Intent
import android.graphics.Paint
import android.opengl.Visibility
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.TextViewCompat
import androidx.navigation.Navigation.findNavController
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.network.response.product.IncludeExclude
import com.devfutech.paradisonesia.databinding.ItemProductBinding
import com.devfutech.paradisonesia.databinding.ItemProductDetailDescBinding
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

    inner class ProductViewHolder(private val binding: ItemProductDetailDescBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail) {
            binding.apply {

                /*
                ivProduct.loadAny(item.thumbnail?:""){
                    crossfade(true)
                    error(R.drawable.object_wisata_lombok)
                    //error(R.drawable.ic_image_not_available)
                }

                 */

                Timber.tag("PDA").d("PDAs" + item.wishlist_count)
                tvDetailProductName.text = item.name
                tvDetailCategoryProduct.text = item.sub_category?.name
                tvDetailProductRating.text = item.rating_average
                tvDetailProductReviews.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.reviews_w_value, item.reviews_count.toString())
                tvDetailProductDescription.text = item.description
                tvDetailProductFavorit.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.favorite, item.wishlist_count.toString())

                llDetailProductExpand.llExpandableTanggalPelaksanaan
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llTanggalPelaksanaan, AutoTransition())
                            llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility = View.VISIBLE
                            llDetailProductExpand.ivDownArrowTp.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llTanggalPelaksanaan, AutoTransition())
                            llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility = View.GONE
                            llDetailProductExpand.ivDownArrowTp.rotation = -90f
                        }
                    })

                val include_excludes: List<ProductDetail.Include_Excludes?> = item.include_excludes

                val fasilitias_layanan: List<ProductDetail.Facilities?> = item.facilities


                for(Item in include_excludes){
                    if(Item?.is_include==0){
                        val textView= TextView(this@ProductViewHolder.itemView.context)
                        textView.text = Item.description
                        llDetailProductExpand.llIsiInclude.addView(textView)
                    }
                    else{
                        val textView= TextView(this@ProductViewHolder.itemView.context)
                        textView.text = Item?.description
                        llDetailProductExpand.llIsiExclude.addView(textView)
                    }

                }

                for(Item in fasilitias_layanan){
                    val textView= TextView(this@ProductViewHolder.itemView.context)
                    textView.text = Item?.facility?.get(0)?.name
                    llDetailProductExpand.llIsiFasilitasLayanan.addView(textView)
                }


                llDetailProductExpand.llInclude
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedInclude.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llInclude, AutoTransition())
                            llDetailProductExpand.llExpandedInclude.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowInc.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llInclude, AutoTransition())
                            llDetailProductExpand.llExpandedInclude.visibility = View.GONE
                            llDetailProductExpand.icDownArrowInc.rotation = -90f
                        }
                    })


                llDetailProductExpand.llExclude
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedExclude.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llExclude, AutoTransition())
                            llDetailProductExpand.llExpandedExclude.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowExl.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llExclude, AutoTransition())
                            llDetailProductExpand.llExpandedExclude.visibility = View.GONE
                            llDetailProductExpand.icDownArrowExl.rotation = -90f
                        }
                    })

                llDetailProductExpand.llFasilitasLayanan
                    .setOnClickListener({
                        if(llDetailProductExpand.llExpandedFasilitasLayanan.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFasilitasLayanan, AutoTransition())
                            llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowFasLay.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFasilitasLayanan, AutoTransition())
                            llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.GONE
                            llDetailProductExpand.icDownArrowFasLay.rotation = -90f
                        }
                    })
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
            ItemProductDetailDescBinding.inflate(
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