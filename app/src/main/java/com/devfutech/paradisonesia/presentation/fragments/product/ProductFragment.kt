package com.devfutech.paradisonesia.presentation.fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductFragmentBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.ProductAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.bottomsheet.advance_filter.AdvanceFilterBottomSheet
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.toList
import timber.log.Timber

@AndroidEntryPoint
class ProductFragment : BaseFragment() {

    private val binding: ProductFragmentBinding by lazy {
        ProductFragmentBinding.inflate(layoutInflater)
    }
//    private val ss: ArrayList<Product> = TODO()

    private val viewModel: ProductViewModel by viewModels()
    private val productAdapter by lazy {
        ProductAdapter()
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
        setProducts()
    }

    private fun setAction() {
        viewModel.getProducts(mapOf(
            "show" to "5"
        ))
        binding.apply {
            llFilterCategory.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_CATEGORY)
                filter.show(parentFragmentManager, filter.tag)
            }
            llFilterLocation.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_LOCATION)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterItem.setOnClickListener{
                   // findNavController().safeNavigate(R.id.action_Product)
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_ADVANCE)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterSorting.setOnClickListener{
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_SORTING)
                filter.show(parentFragmentManager, filter.tag)
            }

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
                        Timber.tag("ACTION SORT").d("ACTION_FILTER_CAT" + result)

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

                }
            }

            setFragmentResultListener(FilterBottomSheet.ACTION_SORT) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: ArrayList<Filter> = bundle.getParcelableArrayList(FilterBottomSheet.ITEM_FILTER) ?: arrayListOf()

                Timber.tag("ACTION SORT").d("ACTION_SORT" + result)

            }
        }
    }

    private fun setResourceBackground(isSelected:Boolean): Int {
        return if (isSelected) R.drawable.background_filter_selected else R.drawable.background_filter_unselected
    }

    private fun setProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.product.collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading")
                    is Resource.Failure -> {
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        Timber.tag("FRAGMENT_DATA").d("sxy " + result.data)
                        productAdapter.submitList(result.data)

                        //productAdapter.submitList(result.data?.sortedBy { it.price })

                    }
                    else -> {}
                }
            }
        }
    }

    private fun setProducts(compData: String?) {
        lifecycleScope.launchWhenStarted {
            viewModel.product.collect { result ->
                when (result) {
                    is Resource.Loading -> println("Loading")
                    is Resource.Failure -> {
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        Timber.tag("FRAGMENT_DATA").d("sxy " + result.data)
                        //productAdapter.submitList(result.data)
                        productAdapter.submitList(result.data?.sortedBy {it.price }?.asReversed())

                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            rvProduct.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = productAdapter
            }

            rvProduct.setOnClickListener{

            }
        }
    }

}