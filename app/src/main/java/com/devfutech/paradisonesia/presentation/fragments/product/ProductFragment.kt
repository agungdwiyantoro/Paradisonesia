package com.devfutech.paradisonesia.presentation.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductFragmentBinding
import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.ProductAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductFragment : BaseFragment() {

    private val binding: ProductFragmentBinding by lazy {
        ProductFragmentBinding.inflate(layoutInflater)
    }
//    private val ss: ArrayList<Product> = TODO()
    val args: ProductFragmentArgs by navArgs()

    private val viewModel: ProductViewModel by viewModels()
    private val productAdapter by lazy {
        ProductAdapter(binding.tvResult)
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
        /*
        viewModel.getProducts(mapOf(
            "show" to "5"
        ))



        viewModel.getProductsMap(mapOf(
            "city_code" to "[1505,6104]"
        ))
         */
        binding.apply {
            llFilterCategory.setOnClickListener {
               val filter = FilterBottomSheet(FilterBottomSheet.FILTER_CATEGORY, args.categoryProductID)
               filter.show(parentFragmentManager, filter.tag)
            }

            llFilterLocation.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_LOCATION, args.categoryProductID)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterItem.setOnClickListener{
                   // findNavController().safeNavigate(R.id.action_Product)
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_ADVANCE, args.categoryProductID)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterSorting.setOnClickListener{
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_SORTING, args.categoryProductID)
                filter.show(parentFragmentManager, filter.tag)
            }

            tieSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                    ///setProducts(s.toString())
                    Timber.tag("afterTextChanged").d("tieAfterTextChanged")

                    if(args.categoryProductID==0){
                        if(tieSearch.hasFocus()) {
                            viewModel.getProductAllSearch(
                                mapOf(
                                    "search_key" to s.toString()
                                ), tvResult, requireContext()
                            )
                        }
                    }
                    else{
                        if(tieSearch.hasFocus()) {
                            viewModel.getProductByCategorySearch(
                                mapOf(
                                    "search_key" to s.toString()
                                ), tvResult, requireContext()
                            )
                        }
                    }
                   // tvResult.text = resources.getString(R.string.result, productAdapter.itemCount)

                    //productAdapter.currentList.filter { it.name!!.contains(s.toString()) }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                   // tvResult.text = resources.getString(R.string.result, productAdapter.itemCount)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                   // tvResult.text = resources.getString(R.string.result, productAdapter.itemCount)
                }

            })

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER_SUB_CATEGORY) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: ArrayList<Product.Sub_category> = bundle.getParcelableArrayList(FilterBottomSheet.ITEM_FILTER) ?: arrayListOf()
                val color = if (result.isNotEmpty()) R.color.white else R.color.black
                when (type) {
                    FilterBottomSheet.FILTER_CATEGORY -> {
                        tvFilterCategory.text =
                            if (result.isEmpty()) resources.getString(
                                R.string.label_category)
                            else resources.getString(R.string.label_category_count, result.size)
                        //setProductsCategory(result[0].id!!)
                        val id = mutableListOf<Int?>()
                        for (item in result){
                            id.add(item.id)
                        }

                        if(args.categoryProductID==0){
                            viewModel.getProductAllSearch(mapOf(
                                "sub_category_id" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        }
                        else{
                            viewModel.getProductByCategorySearch(mapOf(
                                "sub_category_id" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        }



                        llFilterCategory.setBackgroundResource(setResourceBackground(result.isNotEmpty()))
                        tvFilterCategory.setTextColor(ContextCompat.getColor(requireContext(),color))
                        ivFilterCategory.setColorFilter(ContextCompat.getColor(requireContext(),color))

                        Timber.tag("ACTION SORT").d("ACTION_FILTER_CAT" + id)
                    }
                }
            }

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER_LOCATION) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: ArrayList<Product.City> =
                    bundle.getParcelableArrayList(FilterBottomSheet.ITEM_FILTER) ?: arrayListOf()
                val color = if (result.isNotEmpty()) R.color.white else R.color.black

                when (type) {
                    FilterBottomSheet.FILTER_LOCATION -> {
                        tvFilterLocation.text =
                            if (result.isEmpty()) resources.getString(
                                R.string.label_location
                            ) else resources.getString(R.string.label_location_count, result.size)

                        val id = mutableListOf<Int?>()
                        for (item in result) {
                            id.add(item.code)
                        }
                        //setProductsLocation(result[0].id!!)
                        viewModel.getProductByLocation(
                            mapOf(
                                "city_code" to id.toString()
                            ), tvResult, requireContext()
                        )

                        Timber.tag("ACTION SORT").d("ACTION_FILTER_LOCATION" + result[0].code)
                        llFilterLocation.setBackgroundResource(setResourceBackground(result.isNotEmpty()))
                        tvFilterLocation.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                color
                            )
                        )
                        ivFilterLocation.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                color
                            )
                        )
                    }
                }
            }


            setFragmentResultListener(FilterBottomSheet.ACTION_SORT) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: ArrayList<Product.City> =
                    bundle.getParcelableArrayList(FilterBottomSheet.ITEM_FILTER) ?: arrayListOf()
                val color = if (result.isNotEmpty()) R.color.white else R.color.black

                when (type) {
                    FilterBottomSheet.FILTER_SORTING -> {
                        viewModel.getProductSort(
                            mapOf(
                                "sub_category_id" to "3"
                            )
                        )
                    }

                }
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

    private fun setProducts(id: String) {
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
                        //productAdapter.submitList(result.data?.sortedBy {it.price }?.asReversed())

                        productAdapter.submitList(result.data?.filter { (result.data)
                            (it.name?.contains(id) == true)})
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setProducts(id: Int) {
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
                        //productAdapter.submitList(result.data?.sortedBy {it.price }?.asReversed())

                        productAdapter.submitList(result.data)

                    }
                    else -> {}
                }
            }
        }
    }

    private fun setProductsCategory(id: Int) {
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
                        //productAdapter.submitList(result.data?.sortedBy {it.price }?.asReversed())

                        productAdapter.submitList(result.data?.filter { (result.data)
                            (it.sub_category?.id == id)})
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setProductsLocation(id: Int) {
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
                        //productAdapter.submitList(result.data?.sortedBy {it.price }?.asReversed())

                        productAdapter.submitList(result.data?.filter { (result.data)
                            (it.city?.province_code == id)})
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupView() {
       // val args: ProductFragmentArgs by navArgs()
       // val categoryID = args.categoryProductID

        binding.apply {
            rvProduct.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = productAdapter
            }
            Timber.tag("NIGERITOS").d("XHIT " + args.categoryProductID)



            //else{
            //    setProducts()
            //}
        }
    }


}