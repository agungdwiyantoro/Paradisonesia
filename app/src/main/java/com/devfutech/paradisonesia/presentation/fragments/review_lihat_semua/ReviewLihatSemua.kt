package com.devfutech.paradisonesia.presentation.fragments.review_lihat_semua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemProductDetailPenilaianProdukBinding
import com.devfutech.paradisonesia.domain.model.ReviewLihatSemua
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.adapter.ProductReviewAdapterLihatSemua
import com.devfutech.paradisonesia.external.adapter.ReviewLihatSemuaAdapter
import com.devfutech.paradisonesia.presentation.base.BaseFragment

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReviewLihatSemua : BaseFragment() {

    private val viewModel: ReviewLihatSemuaViewModel by viewModels()

    private val binding: ItemProductDetailPenilaianProdukBinding by lazy {
        ItemProductDetailPenilaianProdukBinding.inflate(layoutInflater)
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
        val args : ReviewLihatSemuaArgs by navArgs()
        val rating: ReviewLihatSemua = args.ratingAverageRatingCount

        binding.apply {
            tvRatingNum.text = resources.getString(R.string.rb_rating_num, rating.ratingAverage, rating.ratingCount)
            rvChooseStar.visibility = View.VISIBLE
            rvChooseStar.adapter = ReviewLihatSemuaAdapter(mutableListOf("1","2","3","4","5"), rating.id!!, viewModel)
            if(rating.ratingAverage!=null){
                rbTotalRatingReview.rating = rating.ratingAverage.toFloat()
            }
            rvRatingView.adapter = productReviewAdapterLihatSemua
            rvRatingView.updatePadding(0,0, 0, resources.getDimension(R.dimen._100sdp).toInt())
            tvLihatSemua.visibility = View.GONE
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
                        Timber.tag("ProductDetailReviewsNEW").d("Success" + result.data)
                        productReviewAdapterLihatSemua.submitList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }
}