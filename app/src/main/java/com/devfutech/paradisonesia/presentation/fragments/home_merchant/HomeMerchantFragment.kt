package com.devfutech.paradisonesia.presentation.fragments.home_merchant

import android.os.Bundle
import android.os.FileUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.HomeMerchantFragmentBinding
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.BannerAdapter
import com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter.ProductMerchantAdapter
import com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter.TopHomeMerchantAdapter
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.home_customer.HomeCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeMerchantFragment : BaseFragment() {

    private val binding: HomeMerchantFragmentBinding by lazy {
        HomeMerchantFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeMerchantViewModel by viewModels()

    private val topHomeMerchantAdapter by lazy {
       // TopHomeMerchantAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // getTopHomeMerchant()
        //topHomeMerchantAdapter.submitList(mutableListOf(HomeMerchant(500000000, 100, 200)))

        Timber.tag("TOPHOMEKONTOL").d("KUNTUL X HOMEMERCHANTFRAGMENT" )

        setupView()
    }

    private fun setupView(){
        binding.apply {
            lyMerchantTransaction.root.adapter = TopHomeMerchantAdapter(mutableListOf(
                HomeMerchant(getString(R.string.total_earnings), com.devfutech.paradisonesia.external.utils.FileUtils.convertToCurrency(50000000) ),
                HomeMerchant(getString(R.string.total_transaction_this_month),"200"),
                HomeMerchant(getString(R.string.total_product_published), "600")))

            lyMerchantListItem.rvMerchantListItem.adapter = ProductMerchantAdapter(mutableListOf(
                HomeMerchant.MerchantProduct(
                    "SELP",
                    "JKD",
                "JKLD",
                "JKDk",
                500000),
                HomeMerchant.MerchantProduct(
                    "SELP",
                    "JKD",
                    "JKLD",
                    "JKDk",
                    500000)
                )
            )
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