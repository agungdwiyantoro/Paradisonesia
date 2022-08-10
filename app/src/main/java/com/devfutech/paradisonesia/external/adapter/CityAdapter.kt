package com.devfutech.paradisonesia.external.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemRecommendationDestinationBinding
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.external.utils.FileUtils.ltrim

class CityAdapter : ListAdapter<City, CityAdapter.CityViewHolder>(POST_COMPARATOR) {


    inner class CityViewHolder(private val binding: ItemRecommendationDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City) {
            binding.apply {
                ivRecommendationDestination.load(ltrim(item.image.toString())) {
                    crossfade(true)
                    error(R.drawable.ic_image_not_available)
                }
                tvRecommendationDestination.text = item.name
//                root.apply {
//                    setOnClickListener {
//                        findNavController().navigate(ShopFragmentDirections.actionShopFragmentToProductFragment(item))
//                    }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemRecommendationDestinationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<City>() {
            override fun areContentsTheSame(
                oldItem: City,
                newItem: City
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: City,
                newItem: City
            ): Boolean =
                oldItem.code == newItem.code
        }
    }
}