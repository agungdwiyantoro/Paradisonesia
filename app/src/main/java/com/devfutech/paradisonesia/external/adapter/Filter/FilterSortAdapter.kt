package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.databinding.ItemFilterBinding
import com.devfutech.paradisonesia.databinding.SortItemBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.filter.SortFilter
import timber.log.Timber

/**
 * Created by devfutech on 10/8/2021.
 */
class FilterSortAdapter(
    private val onItemSelected: (List<SortFilter>) -> Unit,
) : ListAdapter<SortFilter, FilterSortAdapter.FilterViewHolder>(POST_COMPARATOR) {

    private val itemSelected = mutableListOf(SortFilter(1, 2000, 3000, 4000))//mutableListOf<SortFilter>()

    inner class FilterViewHolder(private val binding: SortItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SortFilter) {
            binding.apply {
                rgSortFilter.apply {

                    setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                        Timber.tag("FILTERSORTADAPTER").d("JOJON " + item)
                        if(radBHighestPrice.isSelected){
                            itemSelected.add(item)

                        }
                        else {
                            itemSelected.remove(item)
                        }
                        onItemSelected(itemSelected)
                    })
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            SortItemBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SortFilter>() {
            override fun areContentsTheSame(
                oldItem: SortFilter,
                newItem: SortFilter
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: SortFilter,
                newItem: SortFilter
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}