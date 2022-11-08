package com.devfutech.paradisonesia.presentation.fragments.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductFragmentBinding
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.PaginationAdapter
import com.devfutech.paradisonesia.external.adapter.ProductAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.extension.visible
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

    private var map : Map<String, String> = emptyMap()
    private val tempID = mutableListOf<Int?>()
    private val tempSubCategoryName = mutableListOf<String?>()
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
        setProductIndex()

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
            val isAllowOrNot : Int
            if(args.categoryProductID?.id==null){
                isAllowOrNot = 0
            }
            else{
                isAllowOrNot = args.categoryProductID?.id!!
            }

            llFilterCategory.setOnClickListener {


               val filter = FilterBottomSheet(FilterBottomSheet.FILTER_CATEGORY, isAllowOrNot)
               filter.show(parentFragmentManager, filter.tag)
            }

            llFilterLocation.setOnClickListener {
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_LOCATION, isAllowOrNot)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterItem.setOnClickListener{
                   // findNavController().safeNavigate(R.id.action_Product)
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_ADVANCE, isAllowOrNot)
                filter.show(parentFragmentManager, filter.tag)
            }

            llFilterSorting.setOnClickListener{
                val filter = FilterBottomSheet(FilterBottomSheet.FILTER_SORTING, isAllowOrNot)
                filter.show(parentFragmentManager, filter.tag)
            }

            tieSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                    ///setProducts(s.toString())
                    Timber.tag("afterTextChanged").d("tieAfterTextChanged")

                    if(args.categoryProductID?.id==null){
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

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER) { _, bundle ->
                val resultSubCategory: ArrayList<Product.Sub_category> = bundle.getParcelableArrayList(FilterBottomSheet.ACTION_FILTER_SUB_CATEGORY) ?: arrayListOf()
                val resultAdvance: AdvanceFilter = bundle.get(FilterBottomSheet.ACTION_FILTER_ADVANCE) as AdvanceFilter
                val resultSort: Int = bundle.getInt(FilterBottomSheet.ACTION_FILTER_SORT)
                val color = if (resultSubCategory.isNotEmpty()) R.color.white else R.color.black

                var map: MutableMap<String, String> = mutableMapOf()

                if(resultSubCategory.isNotEmpty()){
                    val id = mutableListOf<Int?>()
                    for (item in resultSubCategory){
                        id.add(item.id)
                    }
                    map = mutableMapOf("sub_category_id" to id.toString())
                    FilterBottomSheet.map += mapOf("sub_category_id" to id.toString())
                    tvFilterCategory.text = resources.getString(R.string.label_category_count, resultSubCategory.size)

                    llFilterCategory.setBackgroundResource(setResourceBackground(resultSubCategory.isNotEmpty()))
                    tvFilterCategory.setTextColor(ContextCompat.getColor(requireContext(),color))
                    ivFilterCategory.setColorFilter(ContextCompat.getColor(requireContext(),color))
                }

                if(resultAdvance.price.isNotEmpty()){
                    FilterBottomSheet.map += mapOf(
                        "price" to resultAdvance.price.toString()
                    )
                }

                if(resultSort!=0) {
                    if (resultSort == 1) {
                        map += mapOf(
                            "sort_type" to "asc",
                            "sort_by" to "price"
                        )
                        FilterBottomSheet.map += mapOf(
                            "sort_type" to "asc",
                            "sort_by" to "price"
                        )
                    }

                    if (resultSort == 2) {
                        map += mapOf(
                            "sort_type" to "desc",
                            "sort_by" to "price"
                        )

                        FilterBottomSheet.map += mapOf(
                            "sort_type" to "desc",
                            "sort_by" to "price"
                        )
                    }

                    if (resultSort == 3) {
                        map += mapOf(
                            "sort_type" to "desc",
                            "sort_by" to "rating_average"
                        )
                        FilterBottomSheet.map += mapOf(
                            "sort_type" to "desc",
                            "sort_by" to "rating_average"
                        )
                    }
                }

                if(tieSearch.text?.isNotEmpty() == true){
                    FilterBottomSheet.map += mapOf(
                        "search_key" to tieSearch.text.toString()
                    )
                }
                viewModel.getProductAllSearch(FilterBottomSheet.map, tvResult, requireContext())
            }
            /*
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

                       // if(args.categoryProductID?.id==0){
                            viewModel.getProductAllSearch(mapOf(
                                "sub_category_id" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        /*
                        }
                        else{
                            viewModel.getProductByCategorySearch(mapOf(
                                "sub_category_id" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        }


                         */


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

                        if(args.categoryProductID?.id==0){
                            viewModel.getProductAllSearch(mapOf(
                                "city_code" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        }
                        else{
                            viewModel.getProductByCategorySearch(mapOf(
                                "city_code" to id.toString()//listOf(result[0].id).toString()
                            ), tvResult, requireContext())
                        }
                        /*
                        viewModel.getProductByLocation(
                            mapOf(
                                "city_code" to id.toString()
                            ), tvResult, requireContext()
                        )

                         */

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

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER_ADVANCE) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: AdvanceFilter = bundle.get(FilterBottomSheet.ITEM_FILTER) as AdvanceFilter
                //  val color = if (result.isNotEmpty()) R.color.white else R.color.black

                when (type) {
                    FilterBottomSheet.FILTER_ADVANCE -> {
                        // tvFilterCategory.text =
                        //     if (result.isEmpty()) resources.getString(
                        //         R.string.label_category)
                        //      else resources.getString(R.string.label_category_count, 9)
                        //setProductsCategory(result[0].id!!)
                        if(args.categoryProductID?.id==0){
                            viewModel.getProductAllSearch(mapOf(
                                "price" to result.price.toString()
                            ), tvResult, requireContext())
                        }
                        else{
                            viewModel.getProductByCategorySearch(mapOf(
                                "price" to result.price.toString()
                            ), tvResult, requireContext())
                        }

                        /*
                        viewModel.getProductsMap(mapOf(
                            "price" to result.price.toString()
                        ), tvResult, requireContext())
                         */

                        Timber.tag("ACTION_FILTER_SORT").d("SORTx" + result.price)


                    }

                }
            }

            setFragmentResultListener(FilterBottomSheet.ACTION_FILTER_SORT) { _, bundle ->
                val type = bundle.getInt(FilterBottomSheet.ITEM_TYPE)
                val result: Int = bundle.getInt(FilterBottomSheet.ITEM_FILTER)
              //  val color = if (result.isNotEmpty()) R.color.white else R.color.black

                binding.apply {

                    //rvProduct.scrollToPosition(0)
                }

                when (type) {
                    FilterBottomSheet.FILTER_SORTING -> {
                        // tvFilterCategory.text =
                        //     if (result.isEmpty()) resources.getString(
                        //         R.string.label_category)
                        //      else resources.getString(R.string.label_category_count, 9)
                        //setProductsCategory(result[0].id!!)
                        binding.apply {
                            rvProduct.smoothScrollToPosition(0)
                        }

                        Timber.tag("ACTION_FILTER_SORT").d("SORTx" + result)
                        if(args.categoryProductID?.id==0){
                            if (result == 1){
                                viewModel.getProductAllSearch(
                                    mapOf(
                                        "sort_type" to "asc",
                                        "sort_by" to "price"
                                    ), tvResult, requireContext()
                                )
                            }

                            if (result == 2){
                                viewModel.getProductAllSearch(
                                    mapOf(
                                        "sort_type" to "desc",
                                        "sort_by" to "price"
                                    ), tvResult, requireContext()
                                )
                            }

                            if (result == 3){
                                viewModel.getProductAllSearch(
                                    mapOf(
                                        "sort_type" to "desc",
                                        "sort_by" to "rating_average"
                                    ), tvResult, requireContext()
                                )
                            }
                        }
                        else{
                            if (result == 1){
                                viewModel.getProductByCategorySearch(
                                    mapOf(
                                        "sort_type" to "asc",
                                        "sort_by" to "price"
                                    ), tvResult, requireContext()
                                )
                            }

                            if (result == 2){
                                viewModel.getProductByCategorySearch(
                                    mapOf(
                                        "sort_type" to "desc",
                                        "sort_by" to "price"
                                    ), tvResult, requireContext()
                                )
                            }

                            if (result == 3){
                                viewModel.getProductByCategorySearch(
                                    mapOf(
                                        "sort_type" to "desc",
                                        "sort_by" to "rating_average"
                                    ), tvResult, requireContext()
                                )
                            }


                        }
                    }

                }
            }
*/



        }
    }

    private fun setResourceBackground(isSelected:Boolean): Int {
        return if (isSelected) R.drawable.background_filter_selected else R.drawable.background_filter_unselected
    }

    private fun setProductIndex(){
        lifecycleScope.launchWhenStarted {
            viewModel.product.collect{result ->
                when (result) {
                    is Resource.Success -> {
                        val totalPage = result.data?.size?.div(10)

                        val index : MutableList<Int> = mutableListOf()
                        if(totalPage!!>2){
                            binding.llPage.cLPagination.visibility = View.VISIBLE
                        }
                        for(i in 1..totalPage!!){
                            index.add(i)
                        }
                        binding.llPage.rvPages.adapter = PaginationAdapter(index, viewModel, binding.tvResult,requireContext() )
                    }
                    else -> {}
                }
            }
        }
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
                        //val totalPage = result.data?.size?.rem(10)



                        result.data?.filter {
                            tempID.add(it.product_sub_category_id)
                            tempSubCategoryName.add(it.sub_category?.name)
                        }

                        if(FilterBottomSheet.map.isEmpty() == true){
                            FilterBottomSheet.map += mutableMapOf(
                                "sub_category_id" to tempID.distinct().toString(),
                                "sort_type" to "desc",
                                "sort_by" to "rating_average")
                            binding.tvFilterCategory.text = resources.getString(R.string.label_category_count, tempID.distinct().size)
                        }


                      //  binding.tvResult.text = resources.getString(R.string.result, tempID.distinct().size, tempSubCategoryName.distinct().toString().removeSurrounding("[", "]"))
                        binding.tvResult.text = resources.getString(R.string.result, result.data?.size, tempSubCategoryName.distinct().toString().removeSurrounding("[", "]"))
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



            rvProduct.adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    rvProduct.scrollToPosition(0)
                }
            })


            //else{
            //    setProducts()
            //}
        }
    }


}