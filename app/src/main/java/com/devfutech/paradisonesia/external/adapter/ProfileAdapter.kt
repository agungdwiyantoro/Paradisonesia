package com.devfutech.paradisonesia.external.adapter

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.domain.model.user.Customer


class ProfileAdapter : ListAdapter<Customer, ProfileAdapter.ProfileViewHolder>(POST_COMPARATOR) {

    inner class ProfileViewHolder() :


    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Customer>() {
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean =
                oldItem.id == newItem.id
        }
    }
}