package com.devfutech.paradisonesia.presentation.bottomsheet.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.BottomSheetFilterBinding
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.Filter.FilterAdapterLocation
import com.devfutech.paradisonesia.external.adapter.Filter.FilterAdapterSortItem
import com.devfutech.paradisonesia.external.adapter.Filter.FilterAdapterSubCategory
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.base.BaseBottomSheet
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FilterBottomSheet(private val type: Int, private val categoryId: Int) : BaseBottomSheet() {

    private val binding: BottomSheetFilterBinding by lazy {
        BottomSheetFilterBinding.inflate(layoutInflater)
    }
    private val viewModel: FilterBottomSheetViewModel by viewModels()
    private var itemSelectedSubCategory = listOf<Product.Sub_category>()
    private var itemSelectedLocation = listOf<Product.City>()

    private var itemSortSelected = listOf<Int>()
    //listOf<SortFilter>()

    private val filterAdapterSubCategory by lazy {
        FilterAdapterSubCategory(this::onItemSelectedSubCategory)
    }

    private val filterAdapterLocation by lazy {
        FilterAdapterLocation(this::onItemSelectedLocation)
    }

    private val filterSortAdapter by lazy{
        FilterAdapterSortItem(this::onItemSortSelected);
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
        setupAction()
        setProvince()
        setCategory()
    }

    private fun onItemSelectedSubCategory(items: List<Product.Sub_category>) {
        itemSelectedSubCategory = items
    }

    private fun onItemSelectedLocation(items: List<Product.City>) {
        itemSelectedLocation = items
    }

    private fun onItemSortSelected(items: List<Int>){
        itemSortSelected = items
    }

    /*
    private fun setProvince() {
        lifecycleScope.launchWhenStarted {
            viewModel.filterx.collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading")
                    is Resource.Failure -> {
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        filterAdapter.submitList(result.data)
                    }
                    else -> {}

                }
            }
        }
    }

     */

    private fun setCategory() {
        lifecycleScope.launchWhenStarted {
            viewModel.filterSubCategory.collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading")
                    is Resource.Failure -> {
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        filterAdapterSubCategory.submitList(result.data?.filter {
                            it?.category?.id == categoryId
                        }?.distinct())
                    }
                    else -> {}

                }
            }
        }
    }

    private fun setProvince(){
        lifecycleScope.launchWhenStarted {
            viewModel.filterLocation.collect{ result ->
                when (result) {
                    is Resource.Loading -> println("Loading")
                    is Resource.Failure -> {
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        filterAdapterLocation.submitList(result.data?.filter {
                            it?.code == categoryId
                        }?.distinct())
                    }

                    else -> {}
                }

            }
        }
    }

    private fun setupAction() {
        when (type) {
            1 -> viewModel.getCategory()
            2 -> viewModel.getProvince()
            3 -> viewModel.getSortList()

            else -> {

            }


        }
        //Timber.tag("type").d("john" + viewModel.getSortList())
        //Timber.tag("type").d("john" + viewModel.getProvince())
        binding.apply {
            btnApplyFilter.setOnClickListener {
                setFragmentResult(
                    ACTION_FILTER_SUB_CATEGORY, bundleOf(
                        ITEM_FILTER to itemSelectedSubCategory,
                        ITEM_TYPE to type
                    )
                )

                setFragmentResult(
                    ACTION_FILTER_LOCATION, bundleOf(
                        ITEM_FILTER to itemSelectedLocation,
                        ITEM_TYPE to type
                    )
                )

                setFragmentResult(
                    ACTION_SORT, bundleOf(
                        ITEM_FILTER to itemSortSelected,
                        ITEM_TYPE to type
                    )
                )



                dismiss()
            }

            FileUtils.dateMask(binding.filterFragment.tieStartDate, binding.filterFragment.tlStartDate)
            FileUtils.dateMask(binding.filterFragment.tieEndDate, binding.filterFragment.tlEndDate)
            binding.filterFragment.ivCalendarStartDate.setOnClickListener({

               // filterFragment.layoutCalendar.root.visibility = View.VISIBLE
            })

            binding.filterFragment.ivCalendarEndDate.setOnClickListener({
               // filterFragment.layoutCalendar.root.visibility = View.VISIBLE
            })

            filterFragment.tiePriceMinim.setText(resources.getString(R.string.final_price, FileUtils.convertToCurrency(filterFragment.sbPrice.valueFrom.toInt())))
            filterFragment.tiePriceMax.setText(resources.getString(R.string.final_price, FileUtils.convertToCurrency(filterFragment.sbPrice.valueTo.toInt())))

            filterFragment.sbPrice.labelBehavior = LabelFormatter.LABEL_GONE

            filterFragment.sbPrice.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
                override fun onStartTrackingTouch(slider: RangeSlider) {
                    val values = slider.values
                    Timber.tag("FilterBottomSheet").d(values[0].toString())
                    Timber.tag("FilterBottomSheet").d(values[1].toString())

                    var minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
                    var maxPrice = resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

                    if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                        val minPriceConverted = String.format("%.0f", values[0])
                        val maxPriceConverted = String.format("%.0f", values[1])
                        minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                        maxPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
                    }

                    filterFragment.tiePriceMinim.setText(minPrice)
                    filterFragment.tiePriceMax.setText(maxPrice)
                }

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    val values = slider.values
                    Timber.tag("FilterBottomSheet").d(values[0].toString())
                    Timber.tag("FilterBottomSheet").d(values[1].toString())

                    var minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
                    var maxPrice = resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

                    if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                        val minPriceConverted = String.format("%.0f", values[0])
                        val maxPriceConverted = String.format("%.0f", values[1])
                        minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                        maxPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
                    }

                    filterFragment.tiePriceMinim.setText(minPrice)
                    filterFragment.tiePriceMax.setText(maxPrice)
                }
            })

            filterFragment.sbPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->

                val values = slider.values
                Timber.tag("FilterBottomSheet").d(values[0].toString())
                Timber.tag("FilterBottomSheet").d(values[1].toString())

                var minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
                var maxPrice = resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

                if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                    val minPriceConverted = String.format("%.0f", values[0])
                    val maxPriceConverted = String.format("%.0f", values[1])
                    minPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                    maxPrice = resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
                }

                filterFragment.tiePriceMinim.setText(minPrice)
                filterFragment.tiePriceMax.setText(maxPrice)
            })
        }
    }

    private fun setupView() {
        binding.apply {
            tvFilterCategory.text = when (type) {
                1 -> "Category"
                2 -> "Location"
                3 -> "Advance filter"
                4 -> "Sort by"
                else -> "Sorting"
            }

            if(type==1) {
                rvFilter.apply {
                    adapter = filterAdapterSubCategory
                    layoutManager = GridLayoutManager(requireContext(), 2)
                }
            }

            if(type==2) {
                rvFilter.apply {
                    adapter = filterAdapterLocation
                    layoutManager = GridLayoutManager(requireContext(), 1)
                }

            }

            if(type==3){
                rvFilter.apply {

                }
            }

            if(type==4){
                rvFilter.apply {
                    adapter = filterSortAdapter
                    layoutManager = GridLayoutManager(requireContext(), 1)
                }

            }
        }
    }

    companion object {
        const val ACTION_FILTER = "action_filter"

        const val ITEM_FILTER = "item_key"
        const val ITEM_TYPE = "item_type"
        const val FILTER_CATEGORY = 1
        const val FILTER_LOCATION = 2
        const val FILTER_ADVANCE = 3
        const val FILTER_SORTING = 4
        const val FILTER_RATING = 3

        const val ACTION_FILTER_SUB_CATEGORY = "ACTION_FILTER_SUB_CATEGORY"
        const val ACTION_FILTER_LOCATION = "ACTION_FILTER_LOCATION"
        const val ACTION_SORT = "ACTION_SORT"
    }
}