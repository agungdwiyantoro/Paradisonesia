package com.devfutech.paradisonesia.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.IsMerchantPreference
import com.devfutech.paradisonesia.databinding.SplashScreenFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment() {

    private val binding:SplashScreenFragmentBinding by lazy {
        SplashScreenFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        if(IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).getIsMerchant()){
            Handler(Looper.getMainLooper()).postDelayed({
                navigationTo(R.id.action_splashScreenFragment_to_homeCustomerFragment)
            }, 3000L)
        }
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                navigationTo(R.id.action_splashScreenFragment_to_homeMerchantFragment)
            }, 3000L)
        }
    }

}