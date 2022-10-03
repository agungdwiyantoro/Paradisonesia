package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.databinding.ItemFilterBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import timber.log.Timber

/**
 * Created by devfutech on 10/8/2021.
 */
class FilterAdapterSubCategory(
    private val onItemSelected: (List<Product.Sub_category>) -> Unit
) : ListAdapter<Product.Sub_category, FilterAdapterSubCategory.FilterViewHolder>(POST_COMPARATOR) {

    private val itemSelected = mutableListOf<Product.Sub_category>()
    private var array = FilterBottomSheet.map.get("sub_category_id")?.removeSurrounding("[", "]")?.replace(" ","")?.split(",")?.map { it.toInt() }

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product.Sub_category) {
            binding.apply {

               // Timber.tag("FACS").d("ALDIANA " + array?.get(index) + ", " + item.id)

                if(array?.contains(item.id) == true){
                    cbItem.isChecked = true
                }
                /*
                if( FilterBottomSheet.map["sub_category_id"]?.get(0)?.digitToInt() ==item.id) {
                    cbItem.apply { check(true) }
                }

                 */

                cbItem.apply {

                    text = item.name
                    if (isChecked) {
                        itemSelected.add(item)
                    } else {
                        itemSelected.remove(item)
                    }

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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Product.Sub_category>() {
            override fun areContentsTheSame(
                oldItem: Product.Sub_category,
                newItem: Product.Sub_category
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Product.Sub_category,
                newItem: Product.Sub_category
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}