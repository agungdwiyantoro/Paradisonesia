package com.devfutech.paradisonesia.external.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemBannerBinding
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.external.utils.FileUtils.ltrim
import timber.log.Timber

class BannerAdapter : ListAdapter<Banner, BannerAdapter.BannerViewHolder>(POST_COMPARATOR) {


    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Banner) {
            binding.apply {
            ivBanner.load(ltrim(item.image.toString())) {
                crossfade(true)
                Timber.tag("ASSSX").d(item.image.toString())
                error(R.drawable.ic_image_not_available)
            }

            ivBanner.setOnClickListener{
                Timber.tag("Banner Adapter Link").d(item.title.toString())
            }


                /*

            root.apply {
                setOnClickListener {
                    findNavController().navigate(
                        ShopFragmentDirections.actionShopFragmentToProductFragment(
                            item
                        )
                    )
                }
            }
            */
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Banner>() {
            override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
                oldItem.id == newItem.id
        }
    }
}