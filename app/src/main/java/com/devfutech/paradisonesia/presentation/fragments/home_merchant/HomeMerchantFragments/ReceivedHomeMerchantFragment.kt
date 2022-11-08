package com.devfutech.paradisonesia.presentation.fragments.home_merchant.HomeMerchantFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devfutech.paradisonesia.databinding.LayoutMerchantListItemBinding
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter.ProductMerchantAdapter
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.home_merchant.HomeMerchantViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ReceivedHomeMerchantFragment : BaseFragment() {

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
                    "Received Product 1",
                    "Hotel",
                "Jl. Malioboro Jl. Dagen No. 80",
                    Date().toString(),
                   300000),
                HomeMerchant.MerchantProduct(
                    "Received Product 2",
                    "Hotel",
                    "Jl. Malioboro Jl. Dagen No. 85",
                    Date().toString(),
                   500000),
                HomeMerchant.MerchantProduct(
                    "Received Product 3",
                    "Hotel",
                    "Jl. Malioboro Jl. Dagen No. 90",
                    Date().toString(),
                    700000)
            ), requireActivity())
           // (activity as MainActivity).setupVpBannerTlBanner(binding.vpBanner, binding.tlBanner, animalsArray )

        }
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