package com.devfutech.paradisonesia.external.adapter.Filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.databinding.ItemFilterBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter

/**
 * Created by devfutech on 10/8/2021.
 */
class FilterAdapter(
    private val onItemSelected: (List<Filter>) -> Unit,
) : ListAdapter<Filter, FilterAdapter.FilterViewHolder>(POST_COMPARATOR) {

    private val itemSelected = mutableListOf<Filter>()

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Filter) {
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Filter>() {
            override fun areContentsTheSame(
                oldItem: Filter,
                newItem: Filter
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Filter,
                newItem: Filter
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}