package com.devfutech.paradisonesia.presentation.fragments.review_lihat_semua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devfutech.paradisonesia.databinding.LayoutLihatSemuaPenilaianProdukBinding
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.ProductDetailAdapter.ProductDetailAdapterReviews
import com.devfutech.paradisonesia.external.adapter.ProductReviewAdapterLihatSemua
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.product.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewLihatSemua : BaseFragment() {

    private val viewModel: ReviewLihatSemuaViewModel by viewModels()

    private val binding: LayoutLihatSemuaPenilaianProdukBinding by lazy {
        LayoutLihatSemuaPenilaianProdukBinding.inflate(layoutInflater)
    }

    private val productReviewAdapterLihatSemua by lazy {
        ProductReviewAdapterLihatSemua()
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
        getProductDetailReviews()
    }

    private fun setupView(){
        binding.apply {

        }
    }

    private fun setupAction(){
        binding.apply {

        }
    }

    private fun getProductDetailReviews(){
        lifecycleScope.launchWhenStarted {
            viewModel.productDetailReviews.collect{ result ->
                when(result) {
                 //   is Resource.Loading -> binding.vfProductDetailExpandable.displayedChild = 0
                  //  is Resource.Failure -> {
                   //     binding.vfProductDetailExpandable.displayedChild = 1
                   //     binding.root.snackBar(result.error)
                   // }
                    is Resource.Success -> {
                    //    binding.vfProductDetailExpandable.displayedChild = 1
                        Timber.tag("ProductDetailReviews").d("Success" + result.data)
                        productReviewAdapterLihatSemua.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }
}