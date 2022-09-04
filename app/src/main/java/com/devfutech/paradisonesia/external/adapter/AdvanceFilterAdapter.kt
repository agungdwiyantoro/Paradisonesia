package com.devfutech.paradisonesia.external.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.databinding.FilterFragmentBinding
import com.devfutech.paradisonesia.databinding.ItemFilterBinding
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.filter.Filter

/**
 * Created by devfutech on 10/8/2021.
 */
class AdvanceFilterAdapter(
    private val onItemSelected: (List<AdvanceFilter>) -> Unit,
) : ListAdapter<AdvanceFilter, AdvanceFilterAdapter.FilterViewHolder>(POST_COMPARATOR) {

    private val itemSelected = mutableListOf<AdvanceFilter>()

    inner class FilterViewHolder(private val binding: FilterFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdvanceFilter) {
            binding.apply {
                ivCalendarStartDate.setOnClickListener(View.OnClickListener {
                    layoutCalendar.llcompDatePicker.visibility = View.VISIBLE
                })

                ivCalendarEndDate.setOnClickListener(View.OnClickListener {
                    layoutCalendar.llcompDatePicker.visibility = View.VISIBLE
                })

                /*
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

                 */

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            FilterFragmentBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<AdvanceFilter>() {
            override fun areContentsTheSame(
                oldItem: AdvanceFilter,
                newItem: AdvanceFilter
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: AdvanceFilter,
                newItem: AdvanceFilter
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}