package com.devfutech.paradisonesia.presentation.fragments.product


import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductDetailFragmentRealBinding

import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.*
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.ProductDetailAdapter
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.ProductDetailAdapterReviews
import com.devfutech.paradisonesia.external.extension.snackBar

import com.devfutech.paradisonesia.presentation.base.BaseFragment

import com.facebook.CallbackManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(){
   // val args : ProductDetailFragmentArgs by navArgs()
    //args.detailProduct
    //private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private val binding: ProductDetailFragmentRealBinding by lazy{
        ProductDetailFragmentRealBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductDetailViewModel by viewModels()

    private val productDetailAdapterReviews by lazy {
        ProductDetailAdapterReviews()
    }
    private val productDetailAdapter by lazy {
        ProductDetailAdapter()
    }

    private val productDetailBannerAdapter by lazy {
        BannerAdapter()
    }

    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setAction()

        setProductsDetail()
        getProductDetailReviews()



    }

    private fun setAction() {
        /*
        viewModel.getProducts(mapOf(
            "show" to "5"
        ))

         */
        binding.apply {


            /*
            llFilterCategory.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_CATEGORY)
                filter.show(parentFragmentManager, filter.tag)
            }
            llFilterLocation.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_LOCATION)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterItem.setOnClickListener({
                // findNavController().safeNavigate(R.id.action_Product)
            })

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: ArrayList<Filter> = bundle.getParcelableArrayList(FilterBottomSheet.ITEM_FILTER) ?: arrayListOf()
                val color = if (result.isNotEmpty()) R.color.white else R.color.black
                when (type) {
                    FilterBottomSheet.FILTER_CATEGORY -> {
                        tvFilterCategory.text =
                            if (result.isEmpty()) resources.getString(
                                R.string.label_category
                            ) else resources.getString(R.string.label_category_count, result.size)
                        llFilterCategory.setBackgroundResource(setResourceBackground(result.isNotEmpty()))
                        tvFilterCategory.setTextColor(ContextCompat.getColor(requireContext(),color))
                        ivFilterCategory.setColorFilter(ContextCompat.getColor(requireContext(),color))
                    }
                    FilterBottomSheet.FILTER_LOCATION -> {
                        tvFilterLocation.text =
                            if (result.isEmpty()) resources.getString(
                                R.string.label_location
                            ) else resources.getString(R.string.label_location_count, result.size)
                        llFilterLocation.setBackgroundResource(setResourceBackground(result.isNotEmpty()))
                        tvFilterLocation.setTextColor(ContextCompat.getColor(requireContext(),color))
                        ivFilterLocation.setColorFilter(ContextCompat.getColor(requireContext(),color))
                    }
                    else -> {

                    }
                }
            }

            */
        }
    }

    private fun setResourceBackground(isSelected:Boolean): Int {
        return if (isSelected) R.drawable.background_filter_selected else R.drawable.background_filter_unselected
    }

    private fun setProductsDetail() {
        lifecycleScope.launchWhenStarted {
            viewModel.product.collect { result ->
                when (result) {
                    is Resource.Loading -> binding.vfProductDetailDesc.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfProductDetailDesc.displayedChild = 1
                        Timber.tag("FRAGMENT_DATA").d("ProductDetailFragmentX " + result.error)
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfProductDetailDesc.displayedChild = 1
                        Timber.tag("FRAGMENT_DATA").d("ProductDetailFragmentX " + result.data)
                        productDetailAdapter.submitList(mutableListOf(result.data))
                        //setupView(result.data!!)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailReviews(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailReviews.collect{ result ->
                when(result) {
                    is Resource.Loading -> binding.vfProductDetailExpandable.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfProductDetailExpandable.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfProductDetailExpandable.displayedChild = 1
                        Timber.tag("ProductDetailReviews").d("Success" + result.data)
                        productDetailAdapterReviews.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }


    private fun setupView() {

        binding.apply {
            //rvProductDetailExpandable.adapter = productDetailAdapter
            llDetailProductExpand.lyPenilaianProduk.rvRatingView.adapter = productDetailAdapterReviews
            llDetailProductExpand.lyPenilaianProduk.rbTotalRatingReview.rating = 2.0f
            llDetailProductExpand.lyPenilaianProduk.tvRatingNum.text = resources.getString(R.string.rb_rating_num,2, productDetailAdapterReviews.itemCount)
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

          //  vpBanner.adapter = productDetailBannerAdapter
          //  vpBanner.isSaveEnabled = false
          //  TabLayoutMediator(tlBanner, vpBanner) { _, _ -> }.attach()
        }

        //setProductsDetail(user.toString())
        //bundle?.getParcelable<ProductParcelable>("SHIT")
        /*
         binding.apply {
             tvDetailProduct.text = user.name
             tvDetailProductDescription.text = user.description
             tvDetailProductRating.text = user.rating_average
             tvDetailProductReviews.text = resources.getString(
                 R.string.reviews_w_value, user.reviews_count.toString()
             )
             tvDetailCategoryProduct.text = user.sub_category_name





             val listData = data

             titleList = ArrayList(listData.keys)

             adapter = CustomExpandableListAdapter(requireContext(), titleList as ArrayList<String>, listData)

             elvProductDetail.setAdapter(adapter)

             elvProductDetail.setOnGroupExpandListener { groupPosition ->

                 root.snackBar("List Expanded")


             }

             elvProductDetail.setOnGroupCollapseListener { groupPosition ->

                 root.snackBar("List Collapsed")

             }

             elvProductDetail.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

                 root.snackBar("Child Clicked")

                 true

             }
         }

          */
    }
}

