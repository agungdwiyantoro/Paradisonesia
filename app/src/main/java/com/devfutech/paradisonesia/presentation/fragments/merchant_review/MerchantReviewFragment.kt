package com.devfutech.paradisonesia.presentation.fragments.merchant_review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devfutech.paradisonesia.databinding.MerchantReviewFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantReviewFragment : BaseFragment() {

    private val binding: MerchantReviewFragmentBinding by lazy {
        MerchantReviewFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}