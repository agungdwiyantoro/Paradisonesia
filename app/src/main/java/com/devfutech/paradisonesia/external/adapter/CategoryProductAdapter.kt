package com.devfutech.paradisonesia.external.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductCategoryBinding
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.devfutech.paradisonesia.external.utils.FileUtils.ltrim
import com.devfutech.paradisonesia.presentation.fragments.home_customer.HomeCustomerFragment
import com.devfutech.paradisonesia.presentation.fragments.home_customer.HomeCustomerFragmentDirections
import com.devfutech.paradisonesia.presentation.fragments.product.ProductFragmentDirections
import timber.log.Timber

class CategoryProductAdapter :
    ListAdapter<CategoryProduct, CategoryProductAdapter.CategoryProductViewHolder>(
        POST_COMPARATOR
    ) {


    inner class CategoryProductViewHolder(private val binding: ItemProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryProduct) {
            binding.apply {
                ivCategoryProuduct.load(ltrim(item.icon.toString())) {
                    crossfade(true)
                    Timber.tag("ASSSX").d(item.icon.toString())
                    error(R.drawable.ic_image_not_available)
                }
                tvCategoryProduct.text = item.name



                root.setOnClickListener{
                    val action = HomeCustomerFragmentDirections.actionHomeCustomerFragmentToProductFragment(item.id!!)//ProductFragmentDirections.actionProductFragmentToProductDetailFragment(productParcelable)
                    root.setOnClickListener{
                        Navigation.findNavController(it).navigate(action)
                    }
                }


//                root.apply {
//                    setOnClickListener {
//                        findNavController().navigate(ShopFragmentDirections.actionShopFragmentToProductFragment(item))
//                    }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        return CategoryProductViewHolder(
            ItemProductCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CategoryProduct>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: CategoryProduct,
                newItem: CategoryProduct
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: CategoryProduct,
                newItem: CategoryProduct
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}