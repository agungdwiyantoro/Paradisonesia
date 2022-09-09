package com.devfutech.paradisonesia.presentation.bottomsheet.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devfutech.paradisonesia.databinding.BottomSheetFilterBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.Filter.FilterAdapter
import com.devfutech.paradisonesia.external.adapter.Filter.FilterSortAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheet(private val type: Int) : BaseBottomSheet() {

    private val binding: BottomSheetFilterBinding by lazy {
        BottomSheetFilterBinding.inflate(layoutInflater)
    }
    private val viewModel: FilterBottomSheetViewModel by viewModels()
    private var itemSelected = listOf<Filter>()
    private var itemSortSelected = listOf<Filter>()

    private val filterAdapter by lazy {
        FilterAdapter(this::onItemSelected)
    }

    private val filterSortAdapter by lazy{
        FilterSortAdapter(this::onItemSortSelected);
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
    }

    private fun onItemSelected(items: List<Filter>) {
        itemSelected = items
    }

    private fun onItemSortSelected(items: List<Filter>){
        itemSortSelected = items
    }

    private fun setProvince() {
        lifecycleScope.launchWhenStarted {
            viewModel.filter.collect { result ->
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

    private fun setupAction() {
        when (type) {
            1 -> viewModel.getCategory()
            2 -> viewModel.getProvince()
            4 -> viewModel.getProvince()
            else -> {

            }


        }
        binding.apply {
            btnApplyFilter.setOnClickListener {
                setFragmentResult(
                    ACTION_FILTER, bundleOf(
                        ITEM_FILTER to itemSelected,
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

            binding.filterFragment.ivCalendarStartDate.setOnClickListener({
               // filterFragment.layoutCalendar.root.visibility = View.VISIBLE
            })

            binding.filterFragment.ivCalendarEndDate.setOnClickListener({
               // filterFragment.layoutCalendar.root.visibility = View.VISIBLE
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

            if(type==3){
                filterFragment.root.visibility = View.VISIBLE
                rvFilter.visibility = View.GONE
            }

            if(type==4){
                if (rvFilter.isVisible) {
                    rvFilter.apply {
                        adapter = filterAdapter
                        layoutManager = GridLayoutManager(requireContext(), 1)
                    }
                }


            }

            if(type==1||type==2) {
                if (rvFilter.isVisible) {
                    rvFilter.apply {
                        adapter = filterAdapter
                        layoutManager = GridLayoutManager(requireContext(), 2)
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION_FILTER = "ation_filter"
        const val ACTION_SORT = "action_sort"
        const val ITEM_FILTER = "item_key"
        const val ITEM_TYPE = "item_type"
        const val FILTER_CATEGORY = 1
        const val FILTER_LOCATION = 2
        const val FILTER_ADVANCE = 3
        const val FILTER_SORTING = 4
        const val FILTER_RATING = 3
    }
}