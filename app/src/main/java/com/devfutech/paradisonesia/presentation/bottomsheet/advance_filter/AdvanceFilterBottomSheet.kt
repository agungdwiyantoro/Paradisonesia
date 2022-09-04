package com.devfutech.paradisonesia.presentation.bottomsheet.advance_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.devfutech.paradisonesia.databinding.BottomSheetAdvanceFilterBinding
import com.devfutech.paradisonesia.databinding.BottomSheetFilterBinding
import com.devfutech.paradisonesia.databinding.FilterFragmentBinding
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.FilterAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AdvanceFilterBottomSheet() : BaseBottomSheet() {

    private val binding: BottomSheetAdvanceFilterBinding by lazy {
        BottomSheetAdvanceFilterBinding.inflate(layoutInflater)
    }
   // private val viewModel: FilterBottomSheetViewModel by viewModels()
    private var itemSelected = listOf<AdvanceFilter>()
    private val filterAdapter by lazy {
       // AdvanceFilter(this::onItemSelected)
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
      //  setProvince()
    }

    private fun setupView() {
        binding.apply {

        }
    }

    private fun setupAction() {
        binding.apply {
            tvFilterCategory.text = "Advance Filter"
        }
    }
    private fun onItemSelected(items: List<AdvanceFilter>) {
        itemSelected = items
    }
/*
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
                dismiss()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            tvFilterCategory.text = when (type) {
                1 -> "Category"
                2 -> "Location"
                else -> "Rating"
            }

            rvFilter.apply {
                adapter = filterAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }


 */
    companion object {
        const val ACTION_FILTER = "ation_filter"
        const val ITEM_FILTER = "item_key"
        const val ITEM_TYPE = "item_type"
        const val FILTER_CATEGORY = 1
        const val FILTER_LOCATION = 2
        const val FILTER_RATING = 3
    }
}