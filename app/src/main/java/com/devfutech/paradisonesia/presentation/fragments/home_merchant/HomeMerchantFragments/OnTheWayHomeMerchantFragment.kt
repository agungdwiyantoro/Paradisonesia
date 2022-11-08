package com.devfutech.paradisonesia.presentation.fragments.home_merchant.HomeMerchantFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.util.FileUtil
import com.devfutech.paradisonesia.databinding.LayoutMerchantListItemBinding
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter.ProductMerchantAdapter
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.home_merchant.HomeMerchantViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class OnTheWayHomeMerchantFragment : BaseFragment() {

    private val binding: LayoutMerchantListItemBinding by lazy {
        LayoutMerchantListItemBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeMerchantViewModel by viewModels()

    private val topHomeMerchantAdapter by lazy {
       // TopHomeMerchantAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // getTopHomeMerchant()
        //topHomeMerchantAdapter.submitList(mutableListOf(HomeMerchant(500000000, 100, 200)))
        setupView()
    }

    private fun setupView(){
        binding.apply {
            rvMerchantListItem.adapter = ProductMerchantAdapter(mutableListOf(
                HomeMerchant.MerchantProduct(
                    "On The Way Product 1",
                    "Hotel",
                "Jl. Malioboro Jl. Dagen No. 80",
                    Date().toString(),
                   300000),
                HomeMerchant.MerchantProduct(
                    "On The Way Product 2",
                    "Hotel",
                    "Jl. Malioboro Jl. Dagen No. 85",
                    Date().toString(),
                   500000),
                HomeMerchant.MerchantProduct(
                    "On The Way Product 3",
                    "Hotel",
                    "Jl. Malioboro Jl. Dagen No. 90",
                    Date().toString(),
                    700000),
                HomeMerchant.MerchantProduct(
                    "On The Way Product 4",
                    "Hotel",
                    "Jl. Malioboro Jl. Dagen No. 100",
                    Date().toString(),
                    900000)
            ), requireActivity())
           // (activity as MainActivity).setupVpBannerTlBanner(binding.vpBanner, binding.tlBanner, animalsArray )
        }


    // Timber.tag("OnTheWayHomeMerchantFragment").d("date " + FileUtils.dateToCalendar(Date()))
    }

    private fun getTopHomeMerchant() {
        lifecycleScope.launchWhenCreated {
            viewModel.topHomeMerchant.collect { result ->
                when (result) {
                  /*  is Resource.Loading -> binding.vfCategoryProduct.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfCategoryProduct.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }

                   */
                    is Resource.Success -> {
                      //  binding.vfCategoryProduct.displayedChild = 1
                      //  topHomeMerchantAdapter.submitList(mutableListOf(HomeMerchant(500000000, 100, 200)))
                    }
                    else -> {}
                }
            }
        }
    }

}