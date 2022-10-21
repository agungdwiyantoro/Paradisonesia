package com.devfutech.paradisonesia.presentation.fragments.merchant_reviewed_status

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.IsMerchantPreference
import com.devfutech.paradisonesia.databinding.MerchantReviewedStatusFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MerchantReviewedStatusFragment : BaseFragment() {

    private val binding:MerchantReviewedStatusFragmentBinding by lazy {
        MerchantReviewedStatusFragmentBinding.inflate(layoutInflater)
    }
    private val args:MerchantReviewedStatusFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            appBar.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            appBar.ivBack.isVisible = true
            appBar.tvTitle.text = resources.getString(R.string.label_merchant_submission)
            vfMerchantSubmissionStatus.displayedChild = when {
                args.status?.isReview() == true -> 0
                args.status?.isAccepted() == true -> 2
                else -> 1
            }
        }
    }

}