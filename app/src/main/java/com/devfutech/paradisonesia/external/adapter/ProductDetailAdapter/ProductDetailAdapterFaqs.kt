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
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import timber.log.Timber

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailAdapterFaqs : ListAdapter<ProductDetail.Faqs, ProductDetailAdapterFaqs.ProductViewHolder>(
    POST_COMPARATOR
) {
    var i = 0

    inner class ProductViewHolder(private val binding: ItemProductDetailFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail.Faqs) {
            binding.apply {
                i++
                tvFaqQuestion.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.faq_question, i, item.question)
                tvFaqAnswer.text = item.answer

                cvFaqQuestion.setOnClickListener({

                    if(cvFaqAnswer.visibility == View.GONE){
                        cvFaqAnswer.visibility = View.VISIBLE
                        ivArrowDown.rotation = 90f
                    }
                    else{
                        cvFaqAnswer.visibility = View.GONE
                        ivArrowDown.rotation = 0f
                    }
                })

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductDetailFaqBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail.Faqs>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail.Faqs,
                newItem: ProductDetail.Faqs
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail.Faqs,
                newItem: ProductDetail.Faqs
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}