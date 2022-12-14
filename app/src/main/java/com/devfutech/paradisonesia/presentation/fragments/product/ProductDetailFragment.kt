package com.devfutech.paradisonesia.presentation.fragments.product


import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductDetailFragmentRealBinding
import com.devfutech.paradisonesia.domain.model.PriceID
import com.devfutech.paradisonesia.domain.model.ReviewLihatSemua

import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.*
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.Schedules.ProductDetailAdapterSchedules
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.Schedules.ProductDetailAdapterSchedulesDays
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapterPaketLainnya
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.utils.FileUtils.convertToCurrency

import com.devfutech.paradisonesia.presentation.base.BaseFragment

import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(){
   // val args : ProductDetailFragmentArgs by navArgs()
    //args.detailProduct
    //private var expandableListView: ExpandableListView? = null
    val args : ProductDetailFragmentArgs by navArgs()
    private val viewModel: ProductDetailViewModel by viewModels()
    private val viewModelProduct: ProductViewModel by viewModels()

    private val binding: ProductDetailFragmentRealBinding by lazy{
        ProductDetailFragmentRealBinding.inflate(layoutInflater)
    }

    private val productDetailDescAdapter by lazy {
        ProductDetailDescAdapter()
    }

    private val productDetailImagesAdapter by lazy {
        ProductDetailAdapterImages()
    }

    private val productDetailSchedules by lazy {
        ProductDetailAdapterSchedules(productDetailSchedulesDays)
    }

    private val productDetailSchedulesDays by lazy {
        ProductDetailAdapterSchedulesDays()
    }

    private val productDetailIncludeAdapter by lazy {
        ProductDetailAdapterIncludesExcludes()
    }

    private val productDetailExcludeAdapter by lazy {
        ProductDetailAdapterIncludesExcludes()
    }

    private val productDetailFasilitasLayananAdapter by lazy{
        ProductDetailAdapterFasilitasLayanan()
    }

    private val productDetailFaqsAdapter by lazy{
        ProductDetailAdapterFaqs()
    }

    private  val productDetailTermsAdapter by lazy{
        ProductDetailAdapterTerms()
    }

    private val productDetailReviewsAdapter by lazy {
        ProductDetailAdapterReviews()
    }

    private val productDetailPaketLainnyaAdapter by lazy {
        ProductDetailAdapterPaketLainnya()
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

        getProductDetailDesc()
        getProductDetailImages()
        getProductDetailSchedules()
        getProductDetailSchedulesDays()
        getProductDetailIncludes()
        getProductDetailExcludes()
        getProductDetailFasilitasLayanan()
        getProductDetailFaqs()
        getProductDetailTerms()
        getProductDetailReviews()
        getProductDetailPaketLainnya()


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

    private fun getProductDetailDesc() {
        lifecycleScope.launchWhenStarted {
            viewModel.productDesc.collect { result ->
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
                        productDetailDescAdapter.submitList(mutableListOf(result.data))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailImages(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailImages.collect{ result ->
                when(result){
                    is Resource.Loading -> binding.vfBanner.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfBanner.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfBanner.displayedChild = 1
                        productDetailImagesAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailSchedules(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailSchedules.collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        productDetailSchedules.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailSchedulesDays(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailSchedulesDays.collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        productDetailSchedulesDays.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailIncludes(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailInclude.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        productDetailIncludeAdapter.submitList(result.data?.filter { (result.data)
                            (it?.is_include == 0)})
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailExcludes(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailInclude.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        productDetailExcludeAdapter.submitList(result.data?.filter { (result.data)
                            (it?.is_include == 1)})
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailFasilitasLayanan(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailFasilitasLayanan.collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        productDetailFasilitasLayananAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailFaqs(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailFaqs.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        productDetailFaqsAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailTerms(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailTerms.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        productDetailTermsAdapter.submitList(result.data)
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

                        if(result.data.isNullOrEmpty()){
                            binding.llDetailProductExpand.vfProductDetailPenilaianProduk.displayedChild = 0
                        }
                        else{
                            binding.llDetailProductExpand.vfProductDetailPenilaianProduk.displayedChild = 1
                            Timber.tag("ProductDetailReviews").d("Success" + result.data)
                            if (result.data.size>2){
                                binding.llDetailProductExpand.lyPenilaianProduk.tvLihatSemua.visibility = View.VISIBLE
                            }
                            productDetailReviewsAdapter.submitList(result.data.take(2))
                        }

                      //  binding.vfProductDetailExpandable.displayedChild = 1
                      //  Timber.tag("ProductDetailReviews").d("Success" + result.data)
                      //  productDetailReviewsAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getProductDetailPaketLainnya(){
        lifecycleScope.launchWhenStarted {
            viewModelProduct.product.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        Timber.tag("NIGX").d("SJIT" + result.data)
                        val resultData  = result.data?.filter {
                            it.sub_category?.id == args.detailProduct.subCategoryId && it.id!= args.detailProduct.id }

                        if (resultData?.size!!>3){
                            binding.llDetailProductExpand.lyPaketLainnya.cvLihatSemua.visibility = View.VISIBLE
                        }
                        productDetailPaketLainnyaAdapter.submitList(resultData.take(3))
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupView() {

        val price:PriceID = args.detailProduct

        binding.apply {

            val reviewLihatSemua = ReviewLihatSemua(
                price.id,
                price.ratingAverage,
                price.ratingCount
            )



            /*
            llDetailProductExpand.lyPenilaianProduk.tvLihatSemuanya.setOnClickListener({
                findNavController().safeNavigate(R.id.productDetailFragment_to_productReviewsLihatSemuaFragment)
            })

             */

            tvPrice.text = resources.getString(R.string.final_price, convertToCurrency(price.price))

            llDetailProductExpand.lySyaratKetentuan.rvItemProductDetailTerms.adapter = productDetailTermsAdapter
            llDetailProductExpand.llSyaratKetentuan.setOnClickListener({
                if(llDetailProductExpand.llExpandedSyaratKetentuan.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedSyaratKetentuan, AutoTransition())
                    llDetailProductExpand.llExpandedSyaratKetentuan.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowSyke.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedSyaratKetentuan, AutoTransition())
                    llDetailProductExpand.llExpandedSyaratKetentuan.visibility = View.GONE
                    llDetailProductExpand.icDownArrowSyke.rotation = -90f
                }
            })

            llItemProductDetailDescription.rvItemProductDetailDesc.adapter = productDetailDescAdapter
            llDetailProductExpand.lyProductDetailFaq.rvProductDetailFaqs.adapter = productDetailFaqsAdapter
            llDetailProductExpand.llFaq.setOnClickListener({
                if(llDetailProductExpand.llExpandedFaq.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedFaq, AutoTransition())
                    llDetailProductExpand.llExpandedFaq.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowFaq.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedFaq, AutoTransition())
                    llDetailProductExpand.llExpandedFaq.visibility = View.GONE
                    llDetailProductExpand.icDownArrowFaq.rotation = -90f
                }
            })

            llDetailProductExpand.lySchedules.rvSchedulesHariKeTitle.adapter = productDetailSchedules
            llDetailProductExpand.llRencanaPerjalanan.setOnClickListener({
                if(llDetailProductExpand.llExpandedRencanaPerjalanan.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedRencanaPerjalanan, AutoTransition())
                    llDetailProductExpand.llExpandedRencanaPerjalanan.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowRp.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedRencanaPerjalanan, AutoTransition())
                    llDetailProductExpand.llExpandedRencanaPerjalanan.visibility = View.GONE
                    llDetailProductExpand.icDownArrowRp.rotation = -90f
                }
            })

            llDetailProductExpand.lyFasilitasLayanan.rvIncludeExclude.adapter = productDetailFasilitasLayananAdapter
            llDetailProductExpand.llFasilitasLayanan.setOnClickListener({
                if(llDetailProductExpand.llExpandedFasilitasLayanan.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedFasilitasLayanan, AutoTransition())
                    llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowFasLay.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedFasilitasLayanan, AutoTransition())
                    llDetailProductExpand.llExpandedFasilitasLayanan.visibility = View.GONE
                    llDetailProductExpand.icDownArrowFasLay.rotation = -90f
                }
            })

            llDetailProductExpand.lyProductDetailInclude.rvIncludeExclude.adapter = productDetailIncludeAdapter
            llDetailProductExpand.llInclude.setOnClickListener({
                if(llDetailProductExpand.llExpandedInclude.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedInclude, AutoTransition())
                    llDetailProductExpand.llExpandedInclude.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowInc.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedInclude, AutoTransition())
                    llDetailProductExpand.llExpandedInclude.visibility = View.GONE
                    llDetailProductExpand.icDownArrowInc.rotation = -90f
                }
            })

            llDetailProductExpand.lyExclude.rvIncludeExclude.adapter = productDetailExcludeAdapter
            llDetailProductExpand.llExclude.setOnClickListener({
                if(llDetailProductExpand.llExpandedExclude.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedExclude, AutoTransition())
                    llDetailProductExpand.llExpandedExclude.visibility = View.VISIBLE
                    llDetailProductExpand.icDownArrowExl.rotation = 0f
                }
                else{
                    TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedExclude, AutoTransition())
                    llDetailProductExpand.llExpandedExclude.visibility = View.GONE
                    llDetailProductExpand.icDownArrowExl.rotation = -90f
                }
            })

            vpBanner.adapter = productDetailImagesAdapter
            vpBanner.isSaveEnabled = false
            TabLayoutMediator(tlBanner, vpBanner) { _, _ -> }.attach()

            llDetailProductExpand.lyPenilaianProduk.rvRatingView.adapter = productDetailReviewsAdapter
            if(price.ratingAverage!=null) {
                llDetailProductExpand.lyPenilaianProduk.rbTotalRatingReview.rating = price.ratingAverage.toFloat()
            }

            llDetailProductExpand.lyPenilaianProduk.tvRatingNum.text = resources.getString(R.string.rb_rating_num, price.ratingAverage, price.ratingCount)
            llDetailProductExpand.llPenilaianProduk.setOnClickListener(
                {
                    if(llDetailProductExpand.llExpandedPenilaianProduk.visibility == View.GONE){
                        TransitionManager.beginDelayedTransition(llDetailProductExpand.llPenilaianProduk, AutoTransition())
                        llDetailProductExpand.llExpandedPenilaianProduk.visibility = View.VISIBLE
                        llDetailProductExpand.icDownArrowPenpro.rotation = 0f
                    }
                    else{
                        TransitionManager.beginDelayedTransition(llDetailProductExpand.llPenilaianProduk, AutoTransition())
                        llDetailProductExpand.llExpandedPenilaianProduk.visibility = View.GONE
                        llDetailProductExpand.icDownArrowPenpro.rotation = -90f
                    }
                }
            )

            val action = ProductDetailFragmentDirections.productDetailFragmentToProductReviewsLihatSemuaFragment(reviewLihatSemua)
            llDetailProductExpand.lyPenilaianProduk.tvLihatSemua.setOnClickListener{
                Navigation.findNavController(it).navigate(action)
            }

            llDetailProductExpand.lyPaketLainnya.rvProductDetailPaketLainnya.adapter = productDetailPaketLainnyaAdapter
            llDetailProductExpand.llPaketLainnya.setOnClickListener(
                {
                    if(llDetailProductExpand.llExpandedPaketLainnya.visibility == View.GONE){
                        TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedPaketLainnya, AutoTransition())
                        llDetailProductExpand.llExpandedPaketLainnya.visibility = View.VISIBLE
                        llDetailProductExpand.icDownArrowPaklain.rotation = 0f
                    }
                    else{
                        TransitionManager.beginDelayedTransition(llDetailProductExpand.llExpandedPaketLainnya, AutoTransition())
                        llDetailProductExpand.llExpandedPaketLainnya.visibility = View.GONE
                        llDetailProductExpand.icDownArrowPaklain.rotation = -90f
                    }
                }
            )

            llDetailProductExpand.lyPaketLainnya.cvLihatSemua.setOnClickListener{
                val actionToDestination = ProductDetailFragmentDirections.actionProductDetailFragmentToProductFragment(price)//ProductFragmentDirections.actionProductFragmentToProductDetailFragment(productParcelable)
                Navigation.findNavController(it).navigate(actionToDestination)
            }


            llDetailProductExpand.lyPenilaianProduk.rvChooseStar.setOnClickListener{


            }

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

