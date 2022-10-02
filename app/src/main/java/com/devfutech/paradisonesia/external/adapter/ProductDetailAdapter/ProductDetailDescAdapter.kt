package com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
//import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductDetailDescBinding
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.external.utils.FileUtils
import timber.log.Timber

/**
 * Created by devfutech on 10/5/2021.
 */
class ProductDetailDescAdapter : ListAdapter<ProductDetail, ProductDetailDescAdapter.ProductViewHolder>(
    POST_COMPARATOR
) {

    var tempRating : String? = null

    inner class ProductViewHolder(private val binding: ItemProductDetailDescBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductDetail) {
            binding.apply {
                tvDetailCategoryProduct.text = item.sub_category?.name
                tvDetailProductName.text = item.name
                tvDetailProductLocation.text = item.city?.name
                Timber.tag(this@ProductDetailDescAdapter.toString()).d(item.end_date?.substring(0,10))
                tvDetailProductTanggalPelaksanaan.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.tanggal_pelaksanaan_w_value, FileUtils.monthToDate(item.start_date), FileUtils.monthToDate(item.end_date))

                tempRating = item.rating_average
                if(item.rating_average.isNullOrEmpty()){
                    tempRating = "0"
                }

                tvDetailProductRating.text = tempRating

                tvDetailProductFavorit.text = this@ProductViewHolder.itemView.context.getString(R.string.favorite, item.wishlist_count)
                tvDetailProductDescription.text = item.description
                tvDetailProductReviews.text = this@ProductViewHolder.itemView.context.getString(R.string.reviews_w_value, item.reviews_count)


                /*
                itemBanner.ivBanner.loadAny(item.thumbnail?:""){
                    crossfade(true)
                    error(R.drawable.object_wisata_lombok)
                    //error(R.drawable.ic_image_not_available)
                }



                Timber.tag("PDA").d("PDAs" + item.wishlist_count)
                tvDetailProductName.text = item.name
                tvDetailCategoryProduct.text = item.sub_category?.name
                tvDetailProductRating.text = item.rating_average
                tvDetailProductReviews.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.reviews_w_value, item.reviews_count.toString())
                tvDetailProductDescription.text = item.description
                tvDetailProductFavorit.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.favorite, item.wishlist_count.toString())

                llDetailProductExpand.llExpandableTanggalPelaksanaan
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llTanggalPelaksanaan, AutoTransition())
                            llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility = View.VISIBLE
                            llDetailProductExpand.ivDownArrowTp.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llTanggalPelaksanaan, AutoTransition())
                            llDetailProductExpand.llExpandedTanggalPelaksanaan.visibility = View.GONE
                            llDetailProductExpand.ivDownArrowTp.rotation = -90f
                        }
                    })

                val include_excludes: List<ProductDetail.Include_Excludes?> = item.include_excludes
                val fasilitias_layanan: List<ProductDetail.Facilities?> = item.facilities
                val faqs: List<ProductDetail.Faqs?> = item.faqs
                val terms: List<ProductDetail.Terms?> = item.terms

                for(Item in include_excludes){
                    if(Item?.is_include==0){
                        val textView= TextView(this@ProductViewHolder.itemView.context)
                        textView.text = Item.description
                        llDetailProductExpand.llIsiInclude.addView(textView)
                    }
                    else{
                        val textView= TextView(this@ProductViewHolder.itemView.context)
                        textView.text = Item?.description
                        llDetailProductExpand.llIsiExclude.addView(textView)
                    }

                }

                for(Item in fasilitias_layanan){
                    val textView= TextView(this@ProductViewHolder.itemView.context)
                    textView.text = Item?.facility?.get(0)?.name
                    llDetailProductExpand.llIsiFasilitasLayanan.addView(textView)
                }

                var i=0
                for (Item in faqs){
                    val textView= TextView(this@ProductViewHolder.itemView.context)

                    i++
                    textView.text = HtmlCompat.fromHtml(this@ProductViewHolder.itemView.context.resources.getString(R.string.question_answer, i.toString(), Item?.question, i.toString(), Item?.answer), HtmlCompat.FROM_HTML_MODE_LEGACY)
                    llDetailProductExpand.llIsiFaq.addView(textView)
                }

                var j=0
                for (Item in terms){
                    val textView= TextView(this@ProductViewHolder.itemView.context)

                    j++
                    textView.text = this@ProductViewHolder.itemView.context.resources.getString(R.string.terms, j, Item?.description)
                    llDetailProductExpand.llIsiSyaratKetentuan.addView(textView)
                }

                llDetailProductExpand.llInclude
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedInclude.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llInclude, AutoTransition())
                            llDetailProductExpand.llExpandedInclude.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowInc.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llInclude, AutoTransition())
                            llDetailProductExpand.llExpandedInclude.visibility = View.GONE
                            llDetailProductExpand.icDownArrowInc.rotation = -90f
                        }
                    })


                llDetailProductExpand.llExclude
                    .setOnClickListener({
                        if (llDetailProductExpand.llExpandedExclude.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llExclude, AutoTransition())
                            llDetailProductExpand.llExpandedExclude.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowExl.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llExclude, AutoTransition())
                            llDetailProductExpand.llExpandedExclude.visibility = View.GONE
                            llDetailProductExpand.icDownArrowExl.rotation = -90f
                        }
                    })

                llDetailProductExpand.llFasilitasLayanan
                    .setOnClickListener({
                        if(llDetailProductExpand.llExpandedFasilitasLayanan.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFasilitasLayanan, AutoTransition())
                            llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowFasLay.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFasilitasLayanan, AutoTransition())
                            llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.GONE
                            llDetailProductExpand.icDownArrowFasLay.rotation = -90f
                        }
                    })

                llDetailProductExpand.llFaq
                    .setOnClickListener({
                        if(llDetailProductExpand.llExpandedFaq.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFaq, AutoTransition())
                            llDetailProductExpand.llExpandedFaq.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowFaq.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llFaq, AutoTransition())
                            llDetailProductExpand.llExpandedFaq.visibility = View.GONE
                            llDetailProductExpand.icDownArrowFaq.rotation = -90f
                        }
                    })

                llDetailProductExpand.llSyaratKetentuan
                    .setOnClickListener({
                        if(llDetailProductExpand.llExpandedSyaratKetentuan.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llSyaratKetentuan, AutoTransition())
                            llDetailProductExpand.llExpandedSyaratKetentuan.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowSyke.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llSyaratKetentuan, AutoTransition())
                            llDetailProductExpand.llExpandedSyaratKetentuan.visibility = View.GONE
                            llDetailProductExpand.icDownArrowSyke.rotation = -90f
                        }
                    })

                llDetailProductExpand.llPenilaianProduk.setOnClickListener(
                    {
                        if(llDetailProductExpand.llExpandedPenilaianProduk.visibility == View.GONE){
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llPenilaianProduk, AutoTransition())
                            llDetailProductExpand.llExpandedPenilaianProduk.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowPenpro.rotation = 0f
                        }
                        else{
                            TransitionManager.beginDelayedTransition(llDetailProductExpand.llPenilaianProduk, AutoTransition())
                            llDetailProductExpand.llExpandedPenilaianProduk.visibility = View.VISIBLE
                            llDetailProductExpand.icDownArrowPenpro.rotation = -90f
                        }
                    }
                )



                 */
                //tvProductRating.text = item.
//                root.apply {
//                    setOnClickListener {
//                        findNavController().navigate(ShopFragmentDirections.actionShopFragmentToProductFragment(item))
//                    }
//                }

                /*



                val bundle  = Bundle()
                bundle.putString("name", item.name)
                bundle.putString("rating_average", item.rating_average)
                bundle.putString("reviews_count", item.reviews_count.toString())
                bundle.putString("reviews_count", item.description)

                val bundle = bundleOf("name", bundle)


                val productParcelable = ProductParcelable(
                    item.name,
                    item.coordinate,
                    item.rating_average,
                    item.reviews_count,
                    item.description,
                    item.sub_category?.name)

                val action = ProductFragmentDirections.actionProductFragmentToProductDetailFragment(productParcelable)
                root.setOnClickListener{
                    findNavController(it).navigate(action)
                }

                 */

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductDetailDescBinding.inflate(
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ProductDetail>() {
            override fun areContentsTheSame(
                oldItem: ProductDetail,
                newItem: ProductDetail
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ProductDetail,
                newItem: ProductDetail
            ): Boolean =
                oldItem.id == newItem.id
        }
    }
}