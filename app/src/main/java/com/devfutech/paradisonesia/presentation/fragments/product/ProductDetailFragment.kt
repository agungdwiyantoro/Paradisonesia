package com.devfutech.paradisonesia.presentation.fragments.product


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ProductDetailFragmentBinding
import com.devfutech.paradisonesia.domain.model.expandable_list_data.ExpandableListData.data
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.CustomExpandableListAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.facebook.CallbackManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(){

    //private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private val binding: ProductDetailFragmentBinding by lazy{
        ProductDetailFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: ProductDetailViewModel by viewModels()

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
        setProducts()



    }

    private fun setAction() {
        viewModel.getProducts(mapOf(
            "show" to "5"
        ))
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
                       // productDetailAdapter.submitList(result.data)
                        //setupView(result.data!!)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupView() {
        val args : ProductDetailFragmentArgs by navArgs()
        val user = args.detailProduct
        //bundle?.getParcelable<ProductParcelable>("SHIT")
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

/*
            elvProductDetail.setOnGroupClickListener {
                    parent, v, groupPosition, id ->  setListViewHeight(parent, groupPosition)
                //svProductDetailScrollView.refreshDrawableState()
                false
            }

 */


/*
            elvProductDetail.setOnGroupExpandListener { groupPosition ->

                //root.snackBar("List Expanded")


                val param = elvProductDetail.getLayoutParams()
                param.height =  elvProductDetail.getHeight()
                elvProductDetail.setLayoutParams(param)
                elvProductDetail.refreshDrawableState()
              //  svProductDetailScrollView.refreshDrawableState()



            }

            elvProductDetail.setOnGroupCollapseListener { groupPosition ->

               // root.snackBar("List Collapsed")
                val param = elvProductDetail.getLayoutParams()
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT
                elvProductDetail.setLayoutParams(param)
                elvProductDetail.refreshDrawableState()
               // svProductDetailScrollView.refreshDrawableState()

            }


 */
            elvProductDetail.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

               // root.snackBar("Child Clicked")

                true

            }


        }
    }

    private fun setListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.getMeasuredHeight()
            if (listView.isGroupExpanded(i) && i != group
                || !listView.isGroupExpanded(i) && i == group
            ) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(
                        i, j, false, null,
                        listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.getMeasuredHeight()
                }
            }
        }
        val params = listView.layoutParams
        var height = (totalHeight
                + listView.dividerHeight * (listAdapter.groupCount - 1))
        if (height < 10) height = 200
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }
}

