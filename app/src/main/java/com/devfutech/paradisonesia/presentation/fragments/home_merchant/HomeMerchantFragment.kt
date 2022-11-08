package com.devfutech.paradisonesia.presentation.fragments.home_merchant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.util.FileUtil
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.HomeMerchantFragmentBinding
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.MainActivity
import com.devfutech.paradisonesia.presentation.base.BaseFragment
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

    val animalsArray = arrayOf(
        "Accepted",
        "Received",
        "On the way"
    )

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
            lyMerchantTransaction.tvTotalEarningValue.text = resources.getString(R.string.final_price, FileUtils.convertToCurrency(15000000))
            lyMerchantTransaction.tvTotalTransactionForThisMonthValue.text = "400"
            lyMerchantTransaction.tvTotalProductPublishedValue.text = "700"

            (activity as MainActivity).setupVpBannerTlBanner(binding.vpBanner, binding.tlBanner, animalsArray )

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