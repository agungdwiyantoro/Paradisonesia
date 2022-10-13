package com.devfutech.paradisonesia.presentation.fragments.home_merchant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devfutech.paradisonesia.databinding.HomeMerchantFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMerchantFragment : BaseFragment() {

    private val binding: HomeMerchantFragmentBinding by lazy {
        HomeMerchantFragmentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView(){
        binding.apply {

        }
    }
}