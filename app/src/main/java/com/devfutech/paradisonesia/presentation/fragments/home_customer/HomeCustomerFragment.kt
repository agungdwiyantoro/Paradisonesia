package com.devfutech.paradisonesia.presentation.fragments.home_customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.HomeCustomerFragmentBinding
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.BannerAdapter
import com.devfutech.paradisonesia.external.adapter.CategoryProductAdapter
import com.devfutech.paradisonesia.external.adapter.CityAdapter
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.ProductDetailAdapter
import com.devfutech.paradisonesia.external.extension.gone
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.MainActivity
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber


@AndroidEntryPoint
class HomeCustomerFragment : BaseFragment() {

    private val binding: HomeCustomerFragmentBinding by lazy {
        HomeCustomerFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeCustomerViewModel by viewModels()

    private val bannerAdapter by lazy {
        BannerAdapter()
    }
    private val categoryProductAdapter by lazy {
        CategoryProductAdapter()
    }
    private val cityAdapter by lazy {
        CityAdapter()
    }

    private val productDetailAdapter by lazy{
        ProductDetailAdapter()
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
        getBanners()
        getCategoryProduct()
        getPopularDestination()
        //getProductDetail()
    }

    private fun getPopularDestination() {
        lifecycleScope.launchWhenCreated {
            viewModel.city.collect { result ->
                when (result) {
                    is Resource.Loading -> binding.vfPopularDestination.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfPopularDestination.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfPopularDestination.displayedChild = 1
                        cityAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getCategoryProduct() {
        lifecycleScope.launchWhenCreated {
            viewModel.categoryProduct.collect { result ->
                when (result) {
                    is Resource.Loading -> binding.vfCategoryProduct.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfCategoryProduct.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfCategoryProduct.displayedChild = 1
                        categoryProductAdapter.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getBanners() {
        lifecycleScope.launchWhenCreated {
            viewModel.banner.collect { result ->
                when (result) {
                    is Resource.Loading -> binding.vfBanner.displayedChild = 0
                    is Resource.Failure -> {
                        binding.vfBanner.displayedChild = 1
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        binding.vfBanner.displayedChild = 1
                        bannerAdapter.submitList(result.data)
                       // slideBanners(result.data!!.size)
                        Timber.tag("HAXIMXXX").d(result.data!!.size.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    /*
    private fun getProductDetail() {
        lifecycleScope.launchWhenCreated {
            viewModel.productDetail.collect { result ->
                when (result) {
                    is Resource.Loading -> print("loading") //binding.vfBanner.displayedChild = 0
                    is Resource.Failure -> {
                        Timber.tag("productDetailFUK").d("HOX" + result.error)
                        binding.root.snackBar(result.error)
                    }
                    is Resource.Success -> {
                        //productDetailAdapter.submitList(result.data)
                       // slideBanners(result.data!!.size)
                        Timber.tag("productDetailFUK").d("HOX" + result.data!!)
                    }
                    else -> {}
                }
            }
        }
    }

     */

    private fun slideBanners(bannerSize : Int){
        var currentItem = binding.vpBanner.currentItem
        lifecycleScope.launchWhenCreated {
            while (true) {
                delay(3000L)
                if (currentItem == bannerSize) {
                    currentItem = 0
                }
                binding.vpBanner.setCurrentItem(currentItem++, true)
            }
        }
    }


    private fun setupAction() {
        binding.apply {
            tvLinkMessage.setOnClickListener {
                if (Firebase.auth.currentUser == null) {
                    navigationTo(R.id.action_homeCustomerFragment_to_signinFragment)
                } else {
                    navigationTo(R.id.action_homeCustomerFragment_to_emailVerificationFragment)
                }
            }
            tvSearchProduct.setOnClickListener { findNavController().navigate(R.id.action_homeCustomerFragment_to_productFragment) }

        }
    }

    private fun setupView() {
        (activity as MainActivity).setCountBadgeInbox(0)
        (activity as MainActivity).setCountBadgeBooking(0)
        binding.apply {
            when {
                Firebase.auth.currentUser == null -> {
                    tvInfoMessage.text = resources.getString(R.string.label_login_instruction)
                }
                Firebase.auth.currentUser?.isEmailVerified == false -> {
                    tvInfoMessage.text =
                        resources.getString(R.string.label_verify_email_instruction)
                }
                else -> {
                    llInformationAccount.gone()
                }
            }
            vpBanner.adapter = bannerAdapter
            vpBanner.isSaveEnabled = false
            TabLayoutMediator(tlBanner, vpBanner) { _, _ -> }.attach()

            rvProductCategory.adapter = categoryProductAdapter
            rvRecommendationDestination.adapter = cityAdapter
        }
    }
}