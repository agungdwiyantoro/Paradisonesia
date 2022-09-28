package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.databinding.ItemFilterBinding
import com.devfutech.paradisonesia.domain.model.product.Product

/**
 * Created by devfutech on 10/8/2021.
 */
class FilterAdapterLocation(
    private val onItemSelected: (List<Product.City>) -> Unit,
) : ListAdapter<Product.City, FilterAdapterLocation.FilterViewHolder>(POST_COMPARATOR) {

    private val itemSelected = mutableListOf<Product.City>()

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product.City) {
            binding.apply {
                cbItem.apply {
                    text = item.name
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            itemSelected.add(item)
                        } else {
                            itemSelected.remove(item)
                        }
                        onItemSelected(itemSelected)
                    }
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Product.City>() {
            override fun areContentsTheSame(
                oldItem: Product.City,
                newItem: Product.City
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Product.City,
                newItem: Product.City
            ): Boolean =
                oldItem.code == newItem.code
        }
    }
}